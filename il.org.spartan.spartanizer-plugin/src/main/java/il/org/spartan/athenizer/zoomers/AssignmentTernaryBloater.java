package il.org.spartan.athenizer.zoomers;

import static il.org.spartan.spartanizer.ast.factory.subject.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import static il.org.spartan.spartanizer.ast.navigate.extract.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.Assignment.*;

import il.org.spartan.athenizer.zoom.zoomers.*;
import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.spartanizer.tipping.categories.*;
import il.org.spartan.utils.*;

/** converts {@code (a?b:c;)} to {@code (if(a) b; else c;)} relevant for
 * assignment <em>ternary</em> also relevant for assignment (<em>ternary</em>)
 * s.e $ = (<ternary) Issue #883 {@link Issue0883}
 * @author Raviv Rachmiel
 * @since 23-12-16 */
public class AssignmentTernaryBloater extends ReplaceCurrentNode<ExpressionStatement>//
    implements Category.Bloater {
  private static final long serialVersionUID = -0x7D806FC1C854EF52L;

  @Override public Examples examples() {
    return //
    convert("temp = (a == 0 ? b:c);") //
        .to("if(a==0) temp = b; else temp = c;") //
    ;
  }
  private static ASTNode innerAssignReplacement(final Expression x, final Expression left, final Operator o) {
    final ConditionalExpression $ = az.conditionalExpression(core(x));
    if ($ == null)
      return null;
    final ExpressionStatement e1 = az.expressionStatement($.getAST().newExpressionStatement(pair(left, then($)).to(o)));
    ExpressionStatement e2 = az.expressionStatement($.getAST().newExpressionStatement(pair(left, elze($)).to(o)));
    if (wizard.eq(left, then($)))
      return pair(e2, null).toIf(make.notOf($.getExpression()));
    if (wizard.eq(left, elze($)))
      e2 = null;
    return pair(e1, e2).toIf($.getExpression());
  }
  private static ASTNode replaceAssignment(final Statement ¢) {
    final ExpressionStatement expressionStatement = az.expressionStatement(¢);
    if (expressionStatement == null)
      return null;
    final Assignment $ = az.assignment(expressionStatement.getExpression());
    return $ == null ? null : innerAssignReplacement(right($), left($), $.getOperator());
  }
  @Override public ASTNode replacement(final ExpressionStatement ¢) {
    return replaceAssignment(¢);
  }
  @Override public String description(@SuppressWarnings("unused") final ExpressionStatement __) {
    return "Expanding a ternary operator to a full if-else statement";
  }
}
