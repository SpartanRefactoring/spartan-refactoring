package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.text.edits.TextEditGroup;

import il.org.spartan.spartanizer.ast.factory.copy;
import il.org.spartan.spartanizer.ast.navigate.compute;
import il.org.spartan.spartanizer.ast.safety.az;
import il.org.spartan.spartanizer.engine.collect;
import il.org.spartan.spartanizer.java.namespace.scope;
import il.org.spartan.spartanizer.tipping.categories.Category;
import il.org.spartan.utils.Examples;

/** Remove unused local
 * @author Yossi Gil
 * @since 2017-01-23 */
public final class LocalInitializedUnusedRemove extends LocalInitialized implements Category.Deadcode {
  private static final long serialVersionUID = -0xBDF3E9975A1F125L;

  public LocalInitializedUnusedRemove() {
    andAlso("Local is unused", () -> collect.usesOf(name).in(scope.of(name)).isEmpty());
  }
  @Override public String description() {
    return "Remove unused local " + name;
  }
  @Override public Examples examples() {
    return //
    convert("\f" //
        + "int print() {\n" //
        + "  int number = 1;\n" //
        + "  System.out.println(\"number\");\n" //
        + "}")
            .to("\f" //
                + "int print() {\n" //
                + "  System.out.println(\"number\");\n" //
                + "}") //
            .ignores("\f" //
                + "int print() {\n" //
                + "  int number = 1;\n" //
                + "  System.out.println(number);\n" //
                + "}") //
    ;
  }
  @Override protected ASTRewrite go(final ASTRewrite $, final TextEditGroup g) {
    final Block b = az.block(declaration.getParent());
    if (b == null)
      return $;
    final ListRewrite l = $.getListRewrite(b, Block.STATEMENTS_PROPERTY);
    for (final Statement ¢ : compute.decompose(initializer()))
      l.insertBefore(copy.of(¢), declaration, g);
    il.org.spartan.spartanizer.ast.factory.remove.deadFragment(current(), $, g);
    return $;
  }
}
