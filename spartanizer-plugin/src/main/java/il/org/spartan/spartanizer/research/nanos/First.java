package il.org.spartan.spartanizer.research.nanos;

import static il.org.spartan.spartanizer.research.TipperFactory.patternTipper;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;

import fluent.ly.as;
import il.org.spartan.spartanizer.research.UserDefinedTipper;
import il.org.spartan.spartanizer.research.nanos.common.NanoPatternTipper;
import il.org.spartan.spartanizer.tipping.Tip;

/** @nano first element in collection, lisp style.
 * @author orimarco {@code marcovitch.ori@gmail.com}
 * @since 2016-12-20 */
public final class First extends NanoPatternTipper<MethodInvocation> {
  private static final long serialVersionUID = 0x6150EC362A52B715L;
  private static final List<UserDefinedTipper<MethodInvocation>> tippers = as.list(patternTipper("$X.get(0)", "first($X)", "lisp: first"));

  @Override public boolean canTip(final MethodInvocation ¢) {
    return anyTips(tippers, ¢);
  }
  @Override public Tip pattern(final MethodInvocation ¢) {
    return firstTip(tippers, ¢);
  }
  @Override public Category category() {
    return Category.Iterative;
  }
  @Override public String description() {
    return "First element in collection";
  }
  @Override public String example() {
    return firstPattern(tippers);
  }
  @Override public String symbolycReplacement() {
    return firstReplacement(tippers);
  }
}
