package il.org.spartan.refactoring.utils;

import static il.org.spartan.hamcrest.CoreMatchers.*;
import static il.org.spartan.hamcrest.MatcherAssert.*;
import static il.org.spartan.refactoring.spartanizations.TESTUtils.*;
import static il.org.spartan.refactoring.utils.ExpressionComparator.*;
import static il.org.spartan.refactoring.utils.Funcs.*;
import static il.org.spartan.refactoring.utils.Into.*;
import static org.eclipse.jdt.core.dom.InfixExpression.Operator.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import il.org.spartan.hamcrest.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;
import org.junit.runners.*;

/**
 * A test suite for class {@link Funcs}
 *
 * @author Yossi Gil
 * @since 2015-07-18
 * @see Funcs
 */
@SuppressWarnings({ "static-method", "javadoc" }) @FixMethodOrder(MethodSorters.NAME_ASCENDING)//
public class FuncsTest {
  @Test public void arrayOfInts() {
    assertThat(shortName(t("int[][] _;")), equalTo("iss"));
  }
  @Test public void asComparisonPrefixlExpression() {
    final PrefixExpression p = mock(PrefixExpression.class);
    doReturn(PrefixExpression.Operator.NOT).when(p).getOperator();
    JunitHamcrestWrappper.assertNull(asComparison(p));
  }
  @Test public void asComparisonTypicalExpression() {
    final InfixExpression i = mock(InfixExpression.class);
    doReturn(GREATER).when(i).getOperator();
    JunitHamcrestWrappper.assertNotNull(asComparison(i));
  }
  @Test public void asComparisonTypicalExpressionFalse() {
    final InfixExpression i = mock(InfixExpression.class);
    doReturn(CONDITIONAL_OR).when(i).getOperator();
    JunitHamcrestWrappper.assertNull(asComparison(i));
  }
  @Test public void asComparisonTypicalInfixFalse() {
    final InfixExpression i = mock(InfixExpression.class);
    doReturn(CONDITIONAL_AND).when(i).getOperator();
    JunitHamcrestWrappper.assertNull(asComparison(i));
  }
  @Test public void asComparisonTypicalInfixIsCorrect() {
    final InfixExpression i = mock(InfixExpression.class);
    doReturn(GREATER).when(i).getOperator();
    assertThat(asComparison(i), is(i));
  }
  @Test public void asComparisonTypicalInfixIsNotNull() {
    final InfixExpression e = mock(InfixExpression.class);
    doReturn(GREATER).when(e).getOperator();
    JunitHamcrestWrappper.assertNotNull(asComparison(e));
  }
  @Test public void chainComparison() {
    assertThat(right(i("a == true == b == c")).toString(), is("c"));
  }
  @Test public void countNonWhiteCharacters() {
    assertThat(countNonWhites(e("1 + 23     *456 + \n /* aa */ 7890")), is(13));
  }
  @Test public void findFirstType() {
    JunitHamcrestWrappper.assertNotNull(t("int _;"));
  }
  @Test public void isDeMorganAND() {
    JunitHamcrestWrappper.assertTrue(Is.deMorgan(CONDITIONAL_AND));
  }
  @Test public void isDeMorganGreater() {
    assertThat(Is.deMorgan(GREATER), is(false));
  }
  @Test public void isDeMorganGreaterEuals() {
    assertThat(Is.deMorgan(GREATER_EQUALS), is(false));
  }
  @Test public void isDeMorganOR() {
    JunitHamcrestWrappper.assertTrue(Is.deMorgan(CONDITIONAL_OR));
  }
  @Test public void listOfInts() {
    assertThat(shortName(t("List<Set<Integer>> _;")), equalTo("iss"));
  }
  @Test public void sameOfNullAndSomething() {
    assertThat(Funcs.same(null, e("a")), is(false));
  }
  @Test public void sameOfNulls() {
    JunitHamcrestWrappper.assertTrue(Funcs.same((ASTNode) null, (ASTNode) null));
  }
  @Test public void negation0Trivial() {
    assertThat(negationLevel(e("a")), is(0));
  }
  @Test public void negation1Trivial() {
    assertThat(negationLevel(e("-a")), is(1));
  }
  @Test public void negationOfExpressionNoNegation() {
    assertThat(negationLevel(e("((((4))))")), is(0));
  }
  @Test public void negationOfExpressionManyNegation() {
    assertThat(negationLevel(e("- - - - (- (-a))")), is(6));
  }
  @Test public void sameOfSomethingAndNull() {
    assertThat(Funcs.same(e("a"), null), is(false));
  }
  @Test public void sameOfTwoExpressionsIdentical() {
    JunitHamcrestWrappper.assertTrue(Funcs.same(e("a+b"), e("a+b")));
  }
  @Test public void sameOfTwoExpressionsNotSame() {
    assertThat(Funcs.same(e("a+b+c"), e("a+b")), is(false));
  }
  @Test public void shortNameASTRewriter() {
    assertThat(shortName(t("ASTRewriter _;")), equalTo("r"));
  }
  @Test public void shortNameDouble() {
    assertThat(shortName(t("double _;")), equalTo("d"));
  }
  @Test public void shortNameExpression() {
    assertThat(shortName(t("Expression _;")), equalTo("e"));
  }
  @Test public void shortNameInfrastructure() {
    assertThat(shortName(t("int _;")), equalTo("i"));
  }
  @Test public void shortNameQualifiedType() {
    assertThat(shortName(t("org.eclipse.jdt.core.dom.InfixExpression _;")), equalTo("e"));
  }
  private Type t(final String codeFragment) {
    return Extract.firstType(s(codeFragment));
  }
}
