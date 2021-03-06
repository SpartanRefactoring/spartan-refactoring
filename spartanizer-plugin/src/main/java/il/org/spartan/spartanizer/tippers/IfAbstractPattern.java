package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.ast.navigate.step.elze;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;

import il.org.spartan.spartanizer.ast.navigate.hop;
import il.org.spartan.spartanizer.ast.safety.iz;
import il.org.spartan.utils.Examples;

/** @author Yossi Gil
 * @since 2015-08-01 */
public abstract class IfAbstractPattern extends NodeMatcher<IfStatement> {
  private static final long serialVersionUID = 1;
  @Property protected Statement then, elze;
  @Property protected Expression condition;
  @Property protected List<Statement> subsequentStatements;

  public IfAbstractPattern() {
    andAlso("Must be an if statement", () -> {
      condition = current.getExpression();
      then = current.getThenStatement();
      elze = current.getElseStatement();
      subsequentStatements = hop.subsequentStatements(current);
      return true;
    });
  }
  @Override public abstract Examples examples();
  static boolean degenerateElse(final IfStatement ¢) {
    return elze(¢) != null && iz.vacuousElse(¢);
  }
}
