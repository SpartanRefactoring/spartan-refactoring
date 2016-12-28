package il.org.spartan.spartanizer.research;

import static il.org.spartan.spartanizer.tippers.TrimmerTestsUtils.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;

import il.org.spartan.spartanizer.research.patterns.*;

/** @author orimarco <tt>marcovitch.ori@gmail.com</tt>
 * @since 2016-12-28 */
@SuppressWarnings("static-method")
public class LazyInitializerTest {
  @Test public void basic() {
    trimmingOf("¢ = ¢ != null ? ¢ : \"\";")//
        .withTipper(ConditionalExpression.class, new DefaultsTo())//
        .withTipper(Assignment.class, new LazyInitializer())//
        .gives("¢ = default¢(¢).to(\"\");")//
        .withTipper(ConditionalExpression.class, new DefaultsTo())//
        .withTipper(Assignment.class, new LazyInitializer())//
        .gives("lazyInitialize(¢).with(()-> \"\");")//
        .stays();
  }
}
