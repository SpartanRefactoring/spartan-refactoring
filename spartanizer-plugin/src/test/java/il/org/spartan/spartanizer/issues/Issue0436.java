package il.org.spartan.spartanizer.issues;

import static fluent.ly.azzert.is;

import org.junit.Test;

import fluent.ly.azzert;
import il.org.spartan.spartanizer.cmdline.JUnitTestMethodFacotry;
import il.org.spartan.spartanizer.leonidas.anonimizeTest;

/** Failing test, originally from {@link anonimizeTest} .
 * @author Yossi Gil
 * @since 2016 */
@SuppressWarnings("static-method")
public class Issue0436 {
  @Test public void testRenamingWithQualified() {
    azzert.that(
        JUnitTestMethodFacotry.shortenIdentifiers(//
            "if(omg == Val) return oomph(omg, Dear.foo());"), //
        is("if(a == A) return b(a, B.c());"));
  }
}
