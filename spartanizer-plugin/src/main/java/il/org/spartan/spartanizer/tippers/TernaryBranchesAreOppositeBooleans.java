package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;

import il.org.spartan.spartanizer.ast.factory.cons;
import il.org.spartan.spartanizer.ast.factory.copy;
import il.org.spartan.spartanizer.ast.factory.make;
import il.org.spartan.spartanizer.ast.navigate.wizard;
import il.org.spartan.spartanizer.tipping.ReplaceCurrentNode;
import il.org.spartan.spartanizer.tipping.categories.Category;

/** converts {@code
 * ¢ ? Boolean.TRUE : Boolean.FALSE;
 * } into {@code
 * ¢
 * } or {@code
 * ¢ ? Boolean.FALSE : Boolean.TRUE;
 * } into {@code
 * !¢
 * }
 * @author Dan Abramovich
 * @since 27-11-2016 */
public class TernaryBranchesAreOppositeBooleans extends ReplaceCurrentNode<ConditionalExpression>//
    implements Category.Theory.Logical {
  private static final long serialVersionUID = -0x7FD835D63A83948AL;

  @Override public ASTNode replacement(final ConditionalExpression ¢) {
    final Expression $ = ¢.getElseExpression(), then = ¢.getThenExpression();
    return wizard.eq($, truee) && wizard.eq(then, falsee) ? cons.not(copy.of(¢.getExpression()))
        : wizard.eq($, falsee) && wizard.eq(then, truee) ? copy.of(¢.getExpression()) : null;
  }

  public static final ASTNode truee = make.ast("Boolean.TRUE"), falsee = make.ast("Boolean.FALSE");

  @Override public String description(@SuppressWarnings("unused") final ConditionalExpression ¢) {
    return "eliminate teranry that evaluates to either Boolean.FALSE or Boolean.TRUE (not just one of these)";
  }
}
