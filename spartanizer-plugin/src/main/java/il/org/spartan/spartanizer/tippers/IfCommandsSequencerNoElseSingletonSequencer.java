package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.ast.factory.misc.insertAfter;
import static il.org.spartan.spartanizer.ast.navigate.step.elze;
import static il.org.spartan.spartanizer.ast.navigate.step.then;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.text.edits.TextEditGroup;

import il.org.spartan.spartanizer.ast.factory.copy;
import il.org.spartan.spartanizer.ast.factory.misc;
import il.org.spartan.spartanizer.ast.factory.subject;
import il.org.spartan.spartanizer.ast.navigate.extract;
import il.org.spartan.spartanizer.ast.navigate.wizard;
import il.org.spartan.spartanizer.ast.safety.iz;
import il.org.spartan.spartanizer.tipping.GoToNextStatement;
import il.org.spartan.spartanizer.tipping.categories.Category;

/** convert {@code if (x) { f(); return a; } else { g(); {} } } into {@code if
 * (x) { f(); return a; } g(); }
 * @author Yossi Gil
 * @since 2015-07-29 */
public final class IfCommandsSequencerNoElseSingletonSequencer extends GoToNextStatement<IfStatement>//
    implements Category.EarlyReturn {
  private static final long serialVersionUID = -0x4B44CE3E76095713L;

  @Override public String description(@SuppressWarnings("unused") final IfStatement __) {
    return "Invert conditional and use next statement";
  }
  @Override protected ASTRewrite go(final ASTRewrite $, final IfStatement s, final Statement nextStatement, final TextEditGroup g) {
    if (!iz.vacuousElse(s) || !iz.sequencer(nextStatement))
      return null;
    final Statement thenS = then(s);
    if (!iz.sequencer(thenS) && !iz.block(thenS))
      return null;
    final IfStatement asVirtualIf = normalized(thenS, nextStatement, s.getExpression());
    if (asVirtualIf == null)
      return null;
    if (wizard.eq(then(asVirtualIf), elze(asVirtualIf))) {
      $.replace(s, then(asVirtualIf), g);
      $.remove(nextStatement, g);
    } else {
      if (!wizard.shoudlInvert(asVirtualIf))
        return null;
      final IfStatement canonicalIf = misc.invert(asVirtualIf);
      final List<Statement> ss = extract.statements(elze(canonicalIf));
      canonicalIf.setElseStatement(null);
      if (ss.subList(0, ss.size() - 1).stream().anyMatch(iz::sequencerComplex))
        ss.remove(ss.size() - 1);
      if (!iz.block(s.getParent())) {
        ss.add(0, canonicalIf);
        $.replace(s, subject.ss(ss).toBlock(), g);
        $.remove(nextStatement, g);
      } else {
        final ListRewrite lr = insertAfter(s, ss, $, g);
        lr.replace(s, canonicalIf, g);
        lr.remove(nextStatement, g);
      }
    }
    return $;
  }
  private static IfStatement normalized(final Statement $, final Statement nextStatement, final Expression x) {
    if (!iz.block($) || wizard.endsWithSequencer($))
      return subject.pair($, nextStatement).toIf(x);
    final List<Statement> ret = extract.statements($);
    return ret.size() < 2 ? null : //
        subject.pair(subject.ss(ret).add(copy.of(nextStatement)).toBlock(), nextStatement).toIf(x);
  }
}
