package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.Utils.*;
import static il.org.spartan.lisp.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.engine.nominal.*;
import il.org.spartan.spartanizer.java.*;
import il.org.spartan.spartanizer.java.namespace.*;
import il.org.spartan.spartanizer.tipping.*;

/** Tested by {@link Issue1115}
 * @author Yossi Gil
 * @since 2016-09 */
public final class LambdaExpressionRenameSingleParameterToCent extends EagerTipper<LambdaExpression>//
    implements TipperCategory.Centification {
  @Override public String description(LambdaExpression ¢) {
    return "Rename paraemter " + onlyOne(parameters(¢)) + " to ¢ ";
  }

  @Override public Tip tip(final LambdaExpression x, final ExclusionManager m) {
    final SimpleName $ = onlyOne(parameters(x)).getName();
    if (in($.getIdentifier(), namer.return¢, "¢", "__", "_"))
      return null;
    Namespace n = Environment.of(x);
    if (n.allows(namer.current) || n.allowsCurrent())
      return null;
    final Block b = body(x);
    if (b == null || haz.variableDefinition(b) || haz.cent(b) || Collect.usesOf($).in(b).isEmpty())
      return null;
    if (m != null)
      m.exclude(x);
    final SimpleName ¢ = x.getAST().newSimpleName("¢");
    return new Tip(description(x), x, getClass()) {
      @Override public void go(final ASTRewrite r, final TextEditGroup g) {
        Tippers.rename($, ¢, x, r, g);
      }
    };
  }
}
