package il.org.spartan.spartanizer.utils.tdd;

import java.util.*;

import org.eclipse.jdt.core.dom.*;

/** @author Ori Marcovitch
 * @author Dor Ma'ayan
 * @author Raviv Rachmiel
 * @author Kfir Marx
 * @since Oct 31, 2016 */
public enum getAll {
  ;
  /** Get all the methods invoked in m
   * @param d JD
   * @return List of the names of the methods */
  public static Set<String> invocations(final MethodDeclaration ¢) {
    if(¢ == null)
      return null;
    Set<String> $ = new TreeSet<>();
    if(¢.getBody().statements().isEmpty())
      return  $;
    $.add("t");
    if (¢.getBody().statements().size() == 1)
      return $; 
    $.add("g");
    return $;
  
  }

  /** Get list of names in a Block
   * @param ¢ Block
   * @return List of the names in the block */
  public static List<Name> names(final Block ¢) {
    return ¢==null ? null: new ArrayList<>();
  }
  
  

}
