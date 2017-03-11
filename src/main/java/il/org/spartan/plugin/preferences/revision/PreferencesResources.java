package il.org.spartan.plugin.preferences.revision;

import java.util.stream.*;

import org.eclipse.jface.preference.*;

import il.org.spartan.bloater.*;
import il.org.spartan.plugin.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.utils.*;

/** TODO: Daniel Mittelman please add a description
 * @author Daniel Mittelman
 * @since Jan 11, 2017 */
public enum PreferencesResources {
  ;
  /** Page description **/
  public static final String PAGE_DESCRIPTION = "Preferences for the Spartanizer plug-in";
  /** General preferences **/
  public static final String PLUGIN_STARTUP_BEHAVIOR_ID = "pref_startup_behavior";
  public static final String PLUGIN_STARTUP_BEHAVIOR_TEXT = "Plugin startup behavior:";
  public static final String[][] PLUGIN_STARTUP_BEHAVIOR_OPTIONS = { //
      { "Remember individual project settings", "remember" }, //
      { "Enable for all projects", "always_on" }, //
      { "Disable for all projects", "always_off" } //
  };
  public static final String NEW_PROJECTS_ENABLE_BY_DEFAULT_ID = "Preference_enable_by_default_for_new_projects";
  public static final String NEW_PROJECTS_ENABLE_BY_DEFAULT_TEXT = "Enable by default for newly created projects";
  public static final String TIPPER_CATEGORY_PREFIX = "il.org.spartan"; // NOT
                                                                        // SAFE
  public static final Bool NEW_PROJECTS_ENABLE_BY_DEFAULT_VALUE = new Bool(true);

  public static String getLabel(final Class<? extends ExpanderCategory> $) {
    return wizard.className($);
  }

  /** An enum holding together all the "enabled spartanizations" options, also
   * allowing to get the set preference value for each of them */
  public enum TipperGroup {
    Abbreviation(TipperCategory.Abbreviation.class), //
    Arithmetic(TipperCategory.Arithmetic.class), //
    Annonimaization(TipperCategory.Annonimization.class), //
    Canonicalization(TipperCategory.Unite.class), //
    CommonFactoring(TipperCategory.CommnonFactoring.class), //
    Centification(TipperCategory.Centification.class), //
    Deadcode(TipperCategory.Deadcode.class), //
    Dollarization(TipperCategory.Dollarization.class), //
    EarlyReturn(TipperCategory.EarlyReturn.class), //
    Idiomatic(TipperCategory.Idiomatic.class), //
    Inlining(TipperCategory.Inlining.class), //
    NOOP(TipperCategory.NOP.class), //
    Nanopatterns(TipperCategory.Nanos.class), //
    ScopeReduction(TipperCategory.ScopeReduction.class), //
    Sorting(TipperCategory.Sorting.class), //
    SyntacticBaggage(TipperCategory.SyntacticBaggage.class), //
    Ternarization(TipperCategory.Ternarization.class), //
    Bloater(TipperCategory.Bloater.class), //
    Shortcut(TipperCategory.Shortcircuit.class), //
    Thrashing(TipperCategory.EmptyCycles.class), //
    NOOPOnBooleans(TipperCategory.NOP.onBooleans.class), //
    NOOPOnStrings(TipperCategory.NOP.onStrings.class), //
    NOOPOnNumbers(TipperCategory.NOP.onNumbers.class), //
    ;
    public static TipperGroup find(final TipperCategory ¢) {
      return find(¢.getClass());
    }

    public static IPreferenceStore store() {
      return Plugin.plugin().getPreferenceStore();
    }

    private static TipperGroup find(final Class<? extends TipperCategory> ¢) {
      return Stream.of(TipperGroup.values()).filter(λ -> λ.clazz.isAssignableFrom(¢)).findFirst().orElse(null);
    }

    private final Class<? extends TipperCategory> clazz;
    public final String id;
    public final String label;

    TipperGroup(final Class<? extends TipperCategory> clazz) {
      this.clazz = clazz;
      id = clazz.getCanonicalName();
      label = clazz.getSimpleName();
    }

    @SuppressWarnings("static-method") public boolean isEnabled() {
      // This preferences implementation is deprecated. Will be removed soon.
      // --or
      // TODO Roth: deprecate old preferences implementation
      // return Plugin.plugin() == null || store().getBoolean(id);
      return true;
    }
  }
}