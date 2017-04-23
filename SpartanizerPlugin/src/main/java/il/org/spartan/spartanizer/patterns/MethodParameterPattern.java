package il.org.spartan.spartanizer.patterns;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.ast.safety.*;

/** Method parameter
 * @author Yossi Gil
 * @since 2017-04-22 */
public abstract class MethodParameterPattern extends FragmentPattern {
  private static final long serialVersionUID = 1L;
  protected MethodDeclaration methodDeclaration;

  public MethodParameterPattern() {
    notNil("Parent is a method declaration", () -> methodDeclaration = az.methodDeclaration(parent));
  }
}