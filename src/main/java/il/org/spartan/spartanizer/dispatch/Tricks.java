package il.org.spartan.spartanizer.dispatch;

import static org.eclipse.jdt.core.dom.ASTNode.*;

import static il.org.spartan.lisp.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;
import org.jetbrains.annotations.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.java.*;
import il.org.spartan.utils.*;

/** A number of utility functions common to all tippers.
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since 2015-07-17 */
public enum Tricks {
  DUMMY_ENUM_INSTANCE_INTRODUCING_SINGLETON_WITH_STATIC_METHODS;
  public static void addAllReplacing(final Collection<Statement> to, @NotNull final Iterable<Statement> from, final Statement substitute,
      final Statement by1, final Iterable<Statement> by2) {
    for (final Statement ¢ : from)
      if (¢ != substitute)
        copy.into(¢, to);
      else {
        copy.into(by1, to);
        copy.into(by2, to);
      }
  }

  public static IfStatement blockIfNeeded(final IfStatement s, @NotNull final ASTRewrite r, final TextEditGroup g) {
    if (!iz.blockRequired(s))
      return s;
    final Block $ = subject.statement(s).toBlock();
    r.replace(s, $, g);
    return (IfStatement) first(statements($));
  }

  public static Expression eliminateLiteral(@NotNull final InfixExpression x, final boolean b) {
    @Nullable final List<Expression> $ = extract.allOperands(x);
    wizard.removeAll(b, $);
    switch ($.size()) {
      case 1:
        return copy.of(first($));
      case 0:
        return x.getAST().newBooleanLiteral(b);
      default:
        return subject.operands($).to(x.getOperator());
    }
  }

  public static ListRewrite insertAfter(@NotNull final Statement where, @NotNull final List<Statement> what, @NotNull final ASTRewrite r,
      final TextEditGroup g) {
    final ListRewrite $ = r.getListRewrite(where.getParent(), Block.STATEMENTS_PROPERTY);
    for (int ¢ = what.size() - 1;; $.insertAfter(what.get(¢--), where, g))
      if (¢ < 0)
        return $;
  }

  public static ListRewrite insertBefore(final Statement where, @NotNull final Iterable<Statement> what, @NotNull final ASTRewrite r,
      final TextEditGroup g) {
    final ListRewrite $ = r.getListRewrite(parent(where), Block.STATEMENTS_PROPERTY);
    what.forEach(λ -> $.insertBefore(λ, where, g));
    return $;
  }

  public static IfStatement makeShorterIf(@NotNull final IfStatement s) {
    @NotNull final List<Statement> then = extract.statements(then(s)), elze = extract.statements(elze(s));
    final IfStatement $ = wizard.invert(s);
    if (then.isEmpty())
      return $;
    final IfStatement main = copy.of(s);
    if (elze.isEmpty())
      return main;
    final int rankThen = Tricks.sequencerRank(last(then)), rankElse = Tricks.sequencerRank(last(elze));
    return rankElse > rankThen || rankThen == rankElse && !Tricks.thenIsShorter(s) ? $ : main;
  }

  public static boolean mixedLiteralKind(@NotNull final Collection<Expression> xs) {
    if (xs.size() <= 2)
      return false;
    int previousKind = -1;
    for (final Expression x : xs)
      if (x instanceof NumberLiteral || x instanceof CharacterLiteral) {
        final int currentKind = new NumericLiteralClassifier(x + "").type().ordinal();
        assert currentKind >= 0;
        if (previousKind == -1)
          previousKind = currentKind;
        else if (previousKind != currentKind)
          return true;
      }
    return false;
  }

  public static void rename(final SimpleName oldName, final SimpleName newName, final ASTNode where, final ASTRewrite r, final TextEditGroup g) {
    new Inliner(oldName, r, g).byValue(newName).inlineInto(collect.usesOf(oldName).in(where).toArray(new SimpleName[0]));
  }

  @NotNull public static ASTRewrite replaceTwoStatements(@NotNull final ASTRewrite r, @NotNull final Statement what, final Statement by,
      final TextEditGroup g) {
    @Nullable final Block parent = az.block(what.getParent());
    @NotNull final List<Statement> siblings = extract.statements(parent);
    final int i = siblings.indexOf(what);
    siblings.remove(i);
    siblings.remove(i);
    siblings.add(i, by);
    final Block $ = parent.getAST().newBlock();
    copy.into(siblings, statements($));
    r.replace(parent, $, g);
    return r;
  }

  public static boolean shoudlInvert(@NotNull final IfStatement s) {
    final int $ = sequencerRank(hop.lastStatement(then(s))), rankElse = sequencerRank(hop.lastStatement(elze(s)));
    return rankElse > $ || $ == rankElse && !Tricks.thenIsShorter(s);
  }

  public static boolean thenIsShorter(@NotNull final IfStatement s) {
    @NotNull final Statement then = then(s), elze = elze(s);
    if (elze == null)
      return true;
    final int s1 = count.lines(then), s2 = count.lines(elze);
    if (s1 < s2)
      return true;
    if (s1 > s2)
      return false;
    assert s1 == s2;
    final int n2 = extract.statements(elze).size(), n1 = extract.statements(then).size();
    if (n1 < n2)
      return true;
    if (n1 > n2)
      return false;
    assert n1 == n2;
    final IfStatement $ = wizard.invert(s);
    return positivePrefixLength($) >= positivePrefixLength(wizard.invert($));
  }

  private static int positivePrefixLength(@NotNull final IfStatement $) {
    return metrics.length($.getExpression(), then($));
  }

  private static int sequencerRank(@NotNull final ASTNode ¢) {
    return lisp2.index(¢.getNodeType(), BREAK_STATEMENT, CONTINUE_STATEMENT, RETURN_STATEMENT, THROW_STATEMENT);
  }
}