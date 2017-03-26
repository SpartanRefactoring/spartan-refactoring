package il.org.spartan.plugin.preferences.revision;

import static il.org.spartan.plugin.preferences.revision.PreferencesResources.*;
import static il.org.spartan.plugin.preferences.revision.PreferencesResources.TipperGroup.*;

import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.List;
import java.util.Map.*;
import java.util.function.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.jetbrains.annotations.*;

import il.org.spartan.*;
import il.org.spartan.plugin.*;
import il.org.spartan.plugin.preferences.revision.XMLSpartan.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.utils.*;

/** Revised global preferences page for the plugin.
 * @author Ori Roth {@code ori.rothh@gmail.com}
 * @since 2017-02-24 */
public class PreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
  private final Bool refreshNeeded = new Bool();
  private final SpartanPropertyListener listener = new SpartanPropertyListener(refreshNeeded);
  private Changes changes;

  public PreferencesPage() {
    super(GRID);
  }

  @Override public boolean performOk() {
    final boolean $ = super.performOk();
    changes.commit();
    return $;
  }

  /** Build the preferences page by adding controls */
  @Override public void createFieldEditors() {
    @NotNull final List<Entry<String, Object>> ps = getProjects();
    changes = new Changes(ps.stream().map(Entry::getValue).collect(toList()));
    addField(new BooleanFieldEditor(NEW_PROJECTS_ENABLE_BY_DEFAULT_ID, NEW_PROJECTS_ENABLE_BY_DEFAULT_TEXT, getFieldEditorParent()));
    addField(new ListSelectionEditor("X", "Configure tips for projects:", getFieldEditorParent(), ps,
        p -> ProjectPreferencesHandler.execute((IProject) p, changes.getPreference((IProject) p), (pp, es) -> changes.update(pp, es)), //
        λ -> changes.getAble((IProject) λ), //
        λ -> changes.update((IProject) λ, Boolean.valueOf(!changes.getAble((IProject) λ).booleanValue())) //
    ));
  }

  /** @return open projects in workspace */
  @NotNull private static List<Entry<String, Object>> getProjects() {
    @NotNull final List<Entry<String, Object>> $ = new ArrayList<>();
    final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    for (@NotNull final IProject p : workspaceRoot.getProjects())
      try {
        if (p.isOpen() && p.hasNature(JavaCore.NATURE_ID))
          $.add(new AbstractMap.SimpleEntry<>(p.getName(), p));
      } catch (@NotNull final CoreException ¢) {
        monitor.log(¢);
      }
    return $;
  }

  @Override public void init(@SuppressWarnings("unused") final IWorkbench __) {
    setPreferenceStore(TipperGroup.store());
    setDescription(PAGE_DESCRIPTION);
    store().addPropertyChangeListener(listener);
  }

  /** An event handler used to re-initialize the {@link Trimmer} spartanization
   * once a tipper preference was modified. */
  static class SpartanPropertyListener implements IPropertyChangeListener {
    private final Bool refreshNeeded;

    SpartanPropertyListener(final Bool refreshNeeded) {
      this.refreshNeeded = refreshNeeded;
    }

    /* (non-Javadoc)
     *
     * @see org.eclipse.jface.preference.PreferencePage#performApply() */
    @Override public void propertyChange(@Nullable final PropertyChangeEvent ¢) {
      if (¢ != null && ¢.getProperty() != null && ¢.getProperty().startsWith(TIPPER_CATEGORY_PREFIX))
        refreshNeeded.set();
      else if (¢ != null && ¢.getProperty() != null && ¢.getProperty().equals(NEW_PROJECTS_ENABLE_BY_DEFAULT_ID) && ¢.getNewValue() != null
          && ¢.getNewValue() instanceof Boolean)
        NEW_PROJECTS_ENABLE_BY_DEFAULT_VALUE.set(((Boolean) ¢.getNewValue()).booleanValue());
    }
  }

  /** A widget containing a list of projects and some buttons. Used to configure
   * specific project. "configure" button is used to open a dialog, allowing the
   * user to define enabled tippers for the project (see
   * {@link ProjectPreferencesHandler}). "en/disable" button allows the user to
   * toggle spartanization nature for the project.
   * @author Ori Roth {@code ori.rothh@gmail.com}
   * @since 2017-02-25 */
  static class ListSelectionEditor extends ListEditor {
    static final String DELIMETER = ",";
    @NotNull final List<Map.Entry<String, Object>> elements;
    @Nullable Button configureButton;
    @Nullable Button ableButton;

    ListSelectionEditor(final String name, final String labelText, @NotNull final Composite parent,
        @NotNull final List<Map.Entry<String, Object>> elements, @NotNull final Consumer<Object> onConfigure,
        @NotNull final Function<Object, Boolean> isAble, @NotNull final Consumer<Object> onAble) {
      super(name, labelText, parent);
      this.elements = new ArrayList<>(elements);
      @NotNull final Composite buttonBox = new Composite(parent, SWT.NULL);
      @NotNull final GridLayout layout = new GridLayout();
      layout.marginWidth = 0;
      buttonBox.setLayout(layout);
      buttonBox.addDisposeListener(λ -> {
        configureButton = null;
        ableButton = null;
      });
      buttonBox.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
      ableButton = new Button(buttonBox, SWT.PUSH);
      ableButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      ableButton.setText("Enable/Disable tips");
      ableButton.setEnabled(false);
      ableButton.setVisible(true);
      ableButton.addSelectionListener(new SelectionListener() {
        @Override public void widgetSelected(@SuppressWarnings("unused") final SelectionEvent __) {
          onSelection();
        }

        @Override public void widgetDefaultSelected(@SuppressWarnings("unused") final SelectionEvent __) {
          onSelection();
        }

        @SuppressWarnings("synthetic-access") void onSelection() {
          final int i = getList().getSelectionIndex();
          if (i < 0)
            return;
          onAble.accept(elements.get(i).getValue());
          if ("Disable tips".equals(ableButton.getText())) {
            ableButton.setText("Enable tips");
            configureButton.setEnabled(false);
          } else if ("Enable tips".equals(ableButton.getText())) {
            ableButton.setText("Disable tips");
            configureButton.setEnabled(true);
          }
        }
      });
      configureButton = new Button(buttonBox, SWT.PUSH);
      configureButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
      configureButton.setText("Configure tips");
      configureButton.setEnabled(false);
      configureButton.setVisible(true);
      configureButton.addSelectionListener(new SelectionListener() {
        @Override public void widgetSelected(@SuppressWarnings("unused") final SelectionEvent __) {
          onSelection();
        }

        @Override public void widgetDefaultSelected(@SuppressWarnings("unused") final SelectionEvent __) {
          onSelection();
        }

        @SuppressWarnings("synthetic-access") void onSelection() {
          final int i = getList().getSelectionIndex();
          if (i >= 0)
            onConfigure.accept(elements.get(i).getValue());
        }
      });
      parent.addDisposeListener(λ -> {
        configureButton = null;
        ableButton = null;
      });
      getList().addSelectionListener(new SelectionListener() {
        @Override @SuppressWarnings("synthetic-access") public void widgetSelected(@SuppressWarnings("unused") final SelectionEvent __) {
          final int i = getList().getSelectionIndex();
          if (i >= 0)
            if (isAble.apply(elements.get(i).getValue()).booleanValue()) {
              ableButton.setText("Disable tips");
              configureButton.setEnabled(true);
            } else {
              ableButton.setText("Enable tips");
              configureButton.setEnabled(false);
            }
        }

        @Override public void widgetDefaultSelected(@SuppressWarnings("unused") final SelectionEvent __) {
          //
        }
      });
    }

    @Override protected void doFillIntoGrid(final Composite parent, final int numColumns) {
      super.doFillIntoGrid(parent, numColumns);
      getButtonBoxControl(parent).dispose();
    }

    @Override @NotNull protected String[] parseString(@Nullable final String stringList) {
      return stringList != null && !stringList.isEmpty() ? stringList.split(DELIMETER) : elements.stream().map(Entry::getKey).toArray(String[]::new);
    }

    @Override @Nullable protected String getNewInputObject() {
      return null;
    }

    @Override protected String createList(final String[] items) {
      return separate.these(items).by(DELIMETER);
    }

    @Override protected void selectionChanged() {
      if (getList() != null && getList().getSelectionIndex() >= 0 && ableButton != null)
        ableButton.setEnabled(true);
    }
  }

  /** Used to document preferences changes for projects, allowing lazy
   * configuration changes.
   * @author Ori Roth {@code ori.rothh@gmail.com}
   * @since 2017-02-25 */
  static class Changes implements Cloneable {
    @NotNull private final Map<IProject, Map<SpartanCategory, SpartanTipper[]>> preferences1;
    @NotNull private final Map<IProject, Set<String>> preferences2;
    @NotNull private final Map<IProject, Boolean> enabled;

    private Changes() {
      preferences1 = new HashMap<>();
      preferences2 = new HashMap<>();
      enabled = new HashMap<>();
    }

    public Changes(@NotNull final Iterable<Object> projects) {
      preferences1 = new HashMap<>();
      preferences2 = new HashMap<>();
      enabled = new HashMap<>();
      for (final Object p : projects) {
        preferences1.put((IProject) p, null);
        preferences2.put((IProject) p, null);
        enabled.put((IProject) p, null);
      }
    }

    @Override @NotNull @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException") protected Changes clone() {
      @NotNull final Changes $ = new Changes();
      $.preferences1.putAll(preferences1);
      $.preferences2.putAll(preferences2);
      $.enabled.putAll(enabled);
      return $;
    }

    public Map<SpartanCategory, SpartanTipper[]> getPreference(final IProject ¢) {
      return preferences1.computeIfAbsent(¢, λ -> XMLSpartan.getTippersByCategories(¢));
    }

    public Boolean getAble(@NotNull final IProject p) {
      final Boolean $ = enabled.get(p);
      if ($ == null)
        try {
          return Boolean.valueOf(p.hasNature(Nature.NATURE_ID));
        } catch (@NotNull final CoreException ¢) {
          monitor.log(¢);
          return Boolean.FALSE;
        }
      return $;
    }

    public void update(final IProject p, final Boolean able) {
      enabled.put(p, able);
    }

    @Nullable public Void update(final IProject p, @NotNull final Set<String> preference) {
      preferences2.put(p, preference);
      for (@NotNull final SpartanTipper[] ts : preferences1.get(p).values())
        for (@NotNull final SpartanTipper ¢ : ts)
          ¢.enable(preference.contains(¢.name()));
      return null;
    }

    public synchronized void commit() {
      clone().commitSelf();
      for (final IProject ¢ : preferences1.keySet()) {
        preferences1.put(¢, null);
        preferences2.put(¢, null);
        enabled.put(¢, null);
      }
    }

    private void commitSelf() {
      new Job("Applying preferences changes") {
        @Override @SuppressWarnings("synthetic-access") protected IStatus run(@NotNull final IProgressMonitor m) {
          m.beginTask("Applying preferences changes", preferences2.keySet().size());
          for (@NotNull final IProject p : preferences2.keySet()) {
            if (preferences2.get(p) != null)
              ProjectPreferencesHandler.commit(p, preferences2.get(p));
            if (enabled.get(p) != null)
              try {
                TipsOnOffToggle.toggleNature(p, enabled.get(p).booleanValue());
              } catch (@NotNull final CoreException ¢) {
                monitor.log(¢);
              }
            m.worked(1);
          }
          m.done();
          return Status.OK_STATUS;
        }
      }.schedule();
    }
  }
}