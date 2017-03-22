package il.org.spartan.spartanizer.utils;

import static il.org.spartan.azzert.*;

import org.junit.*;

import il.org.spartan.*;
import il.org.spartan.utils.*;

/** TODO David Cohen please add a description
 * @author David Cohen
 * @author Shahar Yair
 * @author Zahi Mizrahi
 * @since 16-11-9 * */
@SuppressWarnings("static-method")
// TODO David/Shahar/Zahi: your tests could have been more extensive --yg
public class Issue0801 {
  @Test public void test01() {
    azzert.that(Int.valueOf(5).inner(), is(Integer.valueOf(5)));
  }

  @Test public void test02() {
    azzert.that(Int.valueOf(0).inner(), is(Integer.valueOf(0)));
  }
}
