package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.tippers.TrimmerTestsUtils.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;

import il.org.spartan.spartanizer.research.patterns.*;

/** @author Oren Afek
 * @since 2016 
 * Testing {@link LambdaExpressionRemoveRedundantCurlyBraces } */
@SuppressWarnings("static-method")
public class Issue455 {
  
  @Test public void singleReturnStatementAndSingleParameterd() {
    trimmingOf("new ArrayList<Integer>().map(x -> {return x + 1;});")
        .withTipper(LambdaExpression.class, new LambdaExpressionRemoveRedundantCurlyBraces()).gives("new ArrayList<Integer>().map(x -> x + 1);");
  }

  @Test public void simpleBiFunction() {
    trimmingOf("(x,y) -> {return x + y;}").withTipper(LambdaExpression.class, new LambdaExpressionRemoveRedundantCurlyBraces())
        .gives("(x,y) -> x + y");
  }

  @Test public void simpleProducer() {
    trimmingOf("() -> {return 42;}").withTipper(LambdaExpression.class, new LambdaExpressionRemoveRedundantCurlyBraces()).gives("() -> 42");
  }

  @Test public void emptyReturnStatement() {
    trimmingOf("(x) -> {return;}").withTipper(LambdaExpression.class, new LambdaExpressionRemoveRedundantCurlyBraces()).gives("(x) -> {}");
  }
  
  @Test public void keepsParansOnParamsAsTheyWere(){
    trimmingOf("(x) -> {return x;}").withTipper(LambdaExpression.class, new LambdaExpressionRemoveRedundantCurlyBraces())
    .gives("(x) -> x");
    
    trimmingOf("x -> {return x;}").withTipper(LambdaExpression.class, new LambdaExpressionRemoveRedundantCurlyBraces())
    .gives("x -> x");
  }
}
