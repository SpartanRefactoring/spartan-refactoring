package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.tipping.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Convert {@code if(a){f();return;}g();} into {@code if(a){f();return;}g();}
 * f(); } provided that this {@code if} statement is the last statement in a
 * method.
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since 2016 */
public final class IfPenultimateInMethodFollowedBySingleStatement extends ReplaceToNextStatement<IfStatement>//
    implements TipperCategory.EarlyReturn {
  private static final long serialVersionUID = -9215176071220857924L;

  @NotNull @Override public String description(@NotNull final IfStatement ¢) {
    return "Convert return into else in  if(" + ¢.getExpression() + ")";
  }

  @Override protected ASTRewrite go(@NotNull final ASTRewrite $, final IfStatement s, final Statement nextStatement, final TextEditGroup g) {
    if (elze(s) != null || !iz.lastInMethod(nextStatement))
      return null;
    @NotNull final Statement then = then(s);
    @Nullable final ReturnStatement deleteMe = az.returnStatement(hop.lastStatement(then));
    if (deleteMe == null || deleteMe.getExpression() != null)
      return null;
    $.replace(deleteMe, make.emptyStatement(deleteMe), g);
    Tippers.remove($, nextStatement, g);
    final IfStatement newIf = copy.of(s);
    @Nullable final Block block = az.block(then(newIf));
    if (block != null)
      Tippers.removeLast(statements(block));
    else
      newIf.setThenStatement(make.emptyStatement(newIf));
    newIf.setElseStatement(copy.of(nextStatement));
    $.replace(s, newIf, g);
    Tippers.remove($, nextStatement, g);
    return $;
  }
}
