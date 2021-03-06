package il.org.spartan.spartanizer.plugin.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import fluent.ly.note;

/** Collects widget operation extensions.
 * @author Ori Roth
 * @since 2017-04-26 */
public class WidgetOperationPoint {
  private static final String WIDGET_OPERATION_ID = "il.org.spartan.widgetoperation";
  public static final List<WidgetOperation> allOperations = new ArrayList<>();

  public static void load() throws CoreException {
    final IConfigurationElement[] es = Platform.getExtensionRegistry().getConfigurationElementsFor(WIDGET_OPERATION_ID);
    if (es == null)
      note.bug(new WidgetOperationPoint());
    else
      for (final IConfigurationElement e : es) {
        final Object o = e.createExecutableExtension("class");
        if (o instanceof WidgetOperation)
          allOperations.add((WidgetOperation) o);
        else
          note.bug(new WidgetOperationPoint());
      }
  }
}
