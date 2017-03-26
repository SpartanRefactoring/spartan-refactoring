package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.*;
import org.jetbrains.annotations.*;

import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.engine.nominal.*;
import il.org.spartan.spartanizer.java.*;
import il.org.spartan.spartanizer.tipping.*;

/** Simplify while statements as much as possible (or remove them or parts of
 * them) if and only if it doesn'tipper have any side-effect.
 * @author Dor Ma'ayan
 * @since 2016-09-26 */
public class WhileDeadRemove extends ReplaceCurrentNode<WhileStatement>//
    implements TipperCategory.EmptyCycles {
  private static final long serialVersionUID = -1440560931678543445L;

  @Override protected boolean prerequisite(@NotNull final WhileStatement ¢) {
    return sideEffects.free(¢);
  }

  @Override @NotNull public String description(final WhileStatement ¢) {
    return "Remove :" + trivia.gist(¢);
  }

  @Override public ASTNode replacement(@NotNull final WhileStatement ¢) {
    return ¢.getAST().newBlock();
  }
}