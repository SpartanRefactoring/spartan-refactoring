package il.org.spartan.spartanizer.utils;

import static org.junit.Assert.*;

import org.junit.*;

// TODO, your link does not work, try to hit F3 on the link below. --yg
/** Tests of {@link utils.Str}
 * @author Shimon Azulay
 * @author Idan Atias
 * @since 16-11-11 */
@SuppressWarnings({ "static-method", "javadoc" }) public class Issue819 {
  @Test public void str_test0() {
    final Str s = new Str();
    s.inner = "coverage";
    assertEquals("coverage", s.inner());
  }
  @Test public void str_test1() {
    assertEquals(null, new Str().inner());
  }
}
