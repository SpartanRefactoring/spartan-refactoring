package il.org.spartan.spartanizer.plugin;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTNode;

import fluent.ly.note;
import il.org.spartan.plugin.preferences.revision.XMLSpartan;
import il.org.spartan.spartanizer.tipping.Tipper;

/** Disable tipper, removing it from XML file.
 * @author Ori Roth <tt>ori.rothh@gmail.com</tt>
 * @since 2017-03-29 */
public class DisableTipper {
  /** Disable tipper of this marker.
   * @param m spartanization marker */
  @SuppressWarnings("unchecked") public static void disable(final IMarker m) {
    if (m != null)
      try {
        disable((Class<? extends Tipper<?>>) m.getAttribute(Builder.SPARTANIZATION_TIPPER_KEY), m.getResource().getProject());
      } catch (final CoreException ¢) {
        note.bug(¢);
      }
  }
  /** Disable tipper.
   * @param tipperClass JD
   * @param p current project */
  public static void disable(final Class<? extends Tipper<?>> tipperClass, final IProject p) {
    if (tipperClass == null || p == null || !p.exists() || !p.isOpen())
      return;
    final Set<Class<Tipper<? extends ASTNode>>> enabledTippers = XMLSpartan.enabledTippers(p);
    if (!enabledTippers.contains(tipperClass))
      return;
    enabledTippers.remove(tipperClass);
    XMLSpartan.updateEnabledTippers(p, enabledTippers.stream().map(Class::getSimpleName).collect(toList()));
    try {
      Eclipse.refreshProject(p);
    } catch (InvocationTargetException | CoreException | InterruptedException ¢) {
      note.bug(¢);
    }
  }
}
