package il.org.spartan.spartanizer.wring;

import static il.org.spartan.idiomatic.*;
import static org.eclipse.jdt.core.dom.InfixExpression.Operator.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.assemble.*;
import il.org.spartan.spartanizer.ast.*;

/** Replace <code>(double)X</code> by <code>1.*X</code>
 * @author Alex Kopzon
 * @author Dan Greenstein
 * @since 2016 */
public final class CastToDouble2Multiply1 extends Wring.ReplaceCurrentNode<CastExpression> implements Kind.NoImpact {
  private static NumberLiteral literal(final Expression e) {
    final NumberLiteral $ = e.getAST().newNumberLiteral();
    $.setToken("1.");
    return $;
  }

  private static InfixExpression replacement(final Expression $) {
    return subject.pair(literal($), $).to(TIMES);
  }

  @Override String description(final CastExpression e) {
    return "Use 1.*" + step.expression(e) + " instead of (double)" + step.expression(e);
  }

  @Override ASTNode replacement(final CastExpression e) {
    return eval(//
        () -> replacement(step.expression(e))//
    ).when(//
        step.type(e).isPrimitiveType() && "double".equals("" + step.type(e)) //
    );
  }
}
