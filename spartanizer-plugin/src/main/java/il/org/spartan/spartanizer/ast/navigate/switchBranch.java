package il.org.spartan.spartanizer.ast.navigate;

import static il.org.spartan.spartanizer.ast.navigate.step.expression;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;

import fluent.ly.as;
import fluent.ly.the;
import il.org.spartan.spartanizer.ast.factory.copy;
import il.org.spartan.spartanizer.ast.nodes.metrics.Metrics;
import il.org.spartan.spartanizer.ast.safety.az;
import il.org.spartan.spartanizer.ast.safety.iz;

/**
 * TODO YuvalSimon <yuvaltechnion@gmail.com> , this class is duplicate also
 * please add a description
 * 
 * @author YuvalSimon <yuvaltechnion@gmail.com>
 * @since Jan 15, 2017 [[SuppressWarningsSpartan]]
 */
public class switchBranch {
	public final List<SwitchCase> cases;
	public final List<Statement> statements;
	private int hasDefault;
	private int numOfStatements;
	private int numOfNodes;
	private int depth;
	private int sequencerLevel;
	public static final int MAX_CASES_FOR_SPARTANIZATION = 10;
	final Comparator<switchBranch>[] priorityOrder = as.array(Comparators.byDefault, Comparators.byNumericOrder,
			Comparators.bySequencerLevel, Comparators.byDepth, Comparators.byStatementsNum, Comparators.byNodesNum,
			Comparators.byCasesNum);

	static final class Comparators {
		private static final Comparator<Expression> numericOrder = (e1, e2) -> e1 == null ? 1
				: e2 == null ? -1
						: !iz.intType(e1) ? iz.intType(e2) ? 1 : 0
								: !iz.intType(e2) ? -1 : Integer.parseInt(e1 + "") - Integer.parseInt(e2 + "");
		static final Comparator<switchBranch> byDefault = (final switchBranch o1,
				final switchBranch o2) -> o1.hasDefault() ? 1 : o2.hasDefault() ? -1 : 0;
		static final Comparator<switchBranch> bySequencerLevel = (final switchBranch o1,
				final switchBranch o2) -> o1.sequencerLevel() == 0 || o2.sequencerLevel() == 0 ? 0
						: o1.sequencerLevel() - o2.sequencerLevel();
		static final Comparator<switchBranch> byDepth = comparingInt(switchBranch::depth);
		static final Comparator<switchBranch> byStatementsNum = comparingInt(switchBranch::statementsNum);
		static final Comparator<switchBranch> byNodesNum = comparingInt(switchBranch::nodesNum);
		static final Comparator<switchBranch> byCasesNum = comparingInt(switchBranch::casesNum);
		static final Comparator<switchBranch> byNumericOrder = (final switchBranch o1,
				final switchBranch o2) -> numericOrder.compare(lowestLexicoCase(o1), lowestLexicoCase(o2));

		private static Expression lowestLexicoCase(final switchBranch b) {
			Expression $ = expression(the.firstOf(b.cases()));
			for (final SwitchCase ¢ : b.cases())
				if (numericOrder.compare(expression(¢), $) < 0)
					$ = expression(¢);
			return $;
		}
	}

	public switchBranch(final List<SwitchCase> cases, final List<Statement> statements) {
		this.cases = cases;
		this.statements = statements;
		hasDefault = numOfNodes = numOfStatements = depth = sequencerLevel = -1;
	}

	public List<SwitchCase> cases() {
		return cases;
	}

	public boolean hasDefault() {
		if (hasDefault == -1)
			hasDefault = cases.stream().filter(SwitchCase::isDefault).map(λ -> 1).findFirst().orElse(hasDefault);
		return hasDefault == 1;
	}

	public int depth() {
		if (depth < 0)
			depth = Metrics.depth(statements);
		return depth;
	}

	public int statementsNum() {
		if (numOfStatements < 0)
			numOfStatements = Metrics.countStatements(statements);
		return numOfStatements;
	}

	public int nodesNum() {
		if (numOfNodes < 0)
			numOfNodes = Metrics.nodes(statements);
		return numOfNodes;
	}

	public int casesNum() {
		return cases.size();
	}

	public int sequencerLevel() {
		if (sequencerLevel >= 0)
			return sequencerLevel;
		final int $ = Metrics.countStatementsOfType(statements, ASTNode.THROW_STATEMENT),
				re = Metrics.countStatementsOfType(statements, ASTNode.RETURN_STATEMENT),
				br = Metrics.countStatementsOfType(statements, ASTNode.BREAK_STATEMENT),
				co = Metrics.countStatementsOfType(statements, ASTNode.CONTINUE_STATEMENT), sum = $ + re + br + co;
		assert sum > 0;
		return sequencerLevel = sum > $ && sum > re && sum > br && sum > co ? 0
				: $ > 0 ? 1 : re > 0 ? 2 : br > 0 ? 3 : 4;
	}

	/**
	 * @param ¢
	 * @return returns 1 if _this_ has better metrics than b (i.e should comebefore
	 *         b in the switch), -1 otherwise
	 */
	private boolean compare(final switchBranch ¢) {
		for (final Comparator<switchBranch> c : priorityOrder) {
			final int $ = c.compare(this, ¢);
			if ($ != 0)
				return $ < 0;
		}
		return false;
	}

	public boolean compareTo(final switchBranch ¢) {
		final boolean $ = compare(¢);
		return $ != ¢.compare(this) ? $ : (the.firstOf(cases) + "").compareTo(the.firstOf(¢.cases) + "") < 0;
	}

	private void addAll(final Collection<Statement> ¢) {
		¢.addAll(cases.stream().map(copy::of).collect(toList()));
		¢.addAll(statements.stream().map(copy::of).collect(toList()));
	}

	private static void addAll(final Collection<Statement> ss, final Iterable<switchBranch> bs) {
		bs.forEach(λ -> λ.addAll(ss));
	}

	public static SwitchStatement makeSwitchStatement(final Iterable<switchBranch> bs, final Expression x,
			final AST t) {
		final SwitchStatement $ = t.newSwitchStatement();
		$.setExpression(copy.of(x));
		addAll(step.statements($), bs);
		return $;
	}

	// TODO Yuval Simon: please simplify this code. It is, to be honest, crappy
	// --yg
	public static List<switchBranch> intoBranches(final SwitchStatement n) {
		final List<Statement> l = step.statements(n);
		assert iz.switchCase(the.firstOf(l));
		final List<SwitchCase> c = an.empty.list(); 
		final List<Statement> s =  an.empty.list();
		final List<switchBranch> $ = an.empty.list();
		boolean nextBranch = true;
		for (int ¢ = 0; ¢ < l.size() - 1; ++¢) {
			if (nextBranch) {
				$.add(new switchBranch(c, s));
				nextBranch = false;
				// TODO Yuval = make this into a decent for loop --yg
				while (iz.switchCase(l.get(¢)) && ¢ < l.size() - 1)
					c.add(az.switchCase(l.get(¢++)));
				if (¢ >= l.size() - 1)
					break;
			}
			if (iz.switchCase(l.get(¢ + 1)) && iz.sequencerComplex(l.get(¢)))
				nextBranch = true;
			s.add(l.get(¢));
		}
		if (!iz.switchCase(the.lastOf(l))) {
			s.add(the.lastOf(l));
			if (!iz.sequencerComplex(the.lastOf(l)))
				s.add(n.getAST().newBreakStatement());
		} else {
			if (!nextBranch)
				s.add(the.lastOf(l));
			else {
				$.add(new switchBranch(c, s));
				c.add(az.switchCase(the.lastOf(l)));
			}
			s.add(n.getAST().newBreakStatement());
		}
		return $;
	}

	public boolean hasSameBody(final switchBranch ¢) {
		return wizard.eq(functionalCommands(), ¢.functionalCommands());
	}

	private List<Statement> functionalCommands() {
		final List<Statement> $ = IntStream.range(0, statements.size() - 1).mapToObj(statements::get).collect(toList());
		if (!iz.breakStatement(the.lastOf(statements)))
			$.add(the.lastOf(statements));
		return $;
	}

	public boolean hasFallThrough() {
		return statements.stream().anyMatch(iz::switchCase);
	}

	public boolean hasStatements() {
		return !statements.isEmpty() && !iz.breakStatement(the.firstOf(statements));
	}
}