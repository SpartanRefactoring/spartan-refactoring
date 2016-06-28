package il.org.spartan.refactoring.wring;

import static il.org.spartan.hamcrest.CoreMatchers.*;
import static il.org.spartan.hamcrest.MatcherAssert.*;
import static il.org.spartan.refactoring.spartanizations.TESTUtils.*;
import static il.org.spartan.refactoring.utils.Funcs.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import il.org.spartan.hamcrest.*;
import il.org.spartan.refactoring.utils.*;
import il.org.spartan.refactoring.wring.AbstractWringTest.OutOfScope;
import il.org.spartan.refactoring.wring.AbstractWringTest.Wringed;
import il.org.spartan.utils.Utils;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for {@link Wrings#ADDITION_SORTER}.
 *
 * @author Yossi Gil
 * @since 2014-07-13
 */
@SuppressWarnings({ "javadoc", "static-method" })//
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//
public class IfAssignToFooElseAssignToFooTest {
  static final Wring<IfStatement> WRING = new IfAssignToFooElseAssignToFoo();

  @Test public void checkSteps() {
    assertThat(asSingle("if (a) a = b; else a = c;"), notNullValue());
    final IfStatement s = asIfStatement(asSingle("if (a) a = b; else a = c;"));
    JunitHamcrestWrappper.assertNotNull(s);
    final Assignment then = Extract.assignment(then(s));
    JunitHamcrestWrappper.assertNotNull(then(s).toString(), then);
    final Assignment elze = Extract.assignment(elze(s));
    JunitHamcrestWrappper.assertNotNull(elze);
    assertThat(compatible(then, elze), is(true));
    assertThat(WRING.scopeIncludes(s), is(true));
  }

  @RunWith(Parameterized.class)//
  public static class OutOfScope extends AbstractWringTest.OutOfScope<IfStatement> {
    static String[][] cases = Utils.asArray(//
        new String[] { "Expression vs. Expression", " 6 - 7 < 2 + 1   " }, //
        new String[] { "Literal vs. Literal", "if (a) return b; else c;" }, //
        new String[] { "Simple if return", "if (a) return b; else return c;" }, //
        new String[] { "Simply nested if return", "{if (a)  return b; else return c;}" }, //
        new String[] { "Nested if return", "if (a) {;{{;;return b; }}} else {{{;return c;};;};}" }, //
        new String[] { "Not same assignment", "if (a) a /= b; else a /= c;" }, //
        new String[] { "Another distinct assignment", "if (a) a /= b; else a %= c;" }, //
        null);

    /**
     * Generate test cases for this parameterized class.
     *
     * @return a collection of cases, where each case is an array of three
     *         objects, the test case name, the input, and the file.
     */
    @Parameters(name = DESCRIPTION)//
    public static Collection<Object[]> cases() {
      return collect(cases);
    }
    /** Instantiates the enclosing class ({@link OutOfScope}) */
    public OutOfScope() {
      super(WRING);
    }
  }

  @RunWith(Parameterized.class)//
  @FixMethodOrder(MethodSorters.NAME_ASCENDING)//
  public static class Wringed extends AbstractWringTest.WringedIfStatement {
    private static String[][] cases = Utils.asArray(//
        new String[] { "Simple if assign", "if (a) a = b; else a = c;", "a = a ? b : c;" }, //
        new String[] { "Simple if plus assign", "if (a) a += b; else a += c;", "a += a ? b : c;" }, //
        new String[] { "Simple if plus assign", "if (a) a *= b; else a *= c;", "a *= a ? b : c;" }, //
        null);

    /**
     * Generate test cases for this parameterized class.
     *
     * @return a collection of cases, where each case is an array of three
     *         objects, the test case name, the input, and the file.
     */
    @Parameters(name = DESCRIPTION)//
    public static Collection<Object[]> cases() {
      return collect(cases);
    }
    /**
     * Instantiates the enclosing class ({@link Wringed})
     */
    public Wringed() {
      super(WRING);
    }
  }
}
