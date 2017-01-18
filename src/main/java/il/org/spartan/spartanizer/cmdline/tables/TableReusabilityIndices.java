package il.org.spartan.spartanizer.cmdline.tables;

import java.lang.reflect.*;
import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.InfixExpression.*;

import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.cmdline.*;
import il.org.spartan.spartanizer.java.namespace.*;
import il.org.spartan.tables.*;

/** Collects various reusability indices for a given folder(s)
 * @author Yossi Gil
 * @since 2016 */
public class TableReusabilityIndices extends FolderASTVisitor {
  static {
    clazz = TableReusabilityIndices.class;
  }
  private static Table writer;

  public static boolean increment(final Map<String, Integer> category, final String key) {
    category.put(key, Integer.valueOf(category.get(key).intValue() + 1));
    return true;
  }

  public static void main(final String[] args)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    FolderASTVisitor.main(args);
    System.out.println("Your output is in " + writer.description());
    writer.close();
  }

  static int[] ranks(final Map<?, Integer> i) {
    int n = 0;
    final int $[] = new int[i.size()];
    for (final Integer ¢ : i.values())
      $[n++] = ¢.intValue();
    return $;
  }

  public static int rindex(final int[] ranks) {
    Arrays.sort(ranks);
    int $ = 0;
    for (int ¢ = 0; ¢ < ranks.length; ++¢)
      $ = Math.max($, Math.min(ranks[¢], ranks.length - ¢));
    return $;
  }

  private int maxArity;
  private final Map<String, Map<String, Integer>> usage = new LinkedHashMap<>();
  private final Set<String> defined = new LinkedHashSet<>();

  public Map<String, Integer> addIfNecessary(final String category, final String key) {
    if (usage.get(category) == null)
      usage.put(category, new LinkedHashMap<>());
    final Map<String, Integer> $ = usage.get(category);
    assert $ != null;
    $.putIfAbsent(key, Integer.valueOf(0));
    return $;
  }

  private void addLineToGlobalStatistcs() {
    writer.col("Project", presentSourceName);
    if (usage.get("METHOD") == null)
      return;
    final int rExternal = rExternal();
    final int rIntrernal = rInternal();
    writer//
        .col("External", rExternal) //
        .col("Internal", rIntrernal)//
        .col("Intrnal-External", rIntrernal - rExternal)//
    ;
  }

  void addMissingKeys() {
    for (final Class<? extends ASTNode> ¢ : wizard.classToNodeType.keySet())
      addIfNecessary("NODE-TYPE", Vocabulary.mangle(¢));
    for (final Assignment.Operator ¢ : wizard.assignmentOperators)
      addIfNecessary("ASSIGNMENT", Vocabulary.mangle(¢));
    for (final PrefixExpression.Operator ¢ : wizard.prefixOperators)
      addIfNecessary("PREFIX", Vocabulary.mangle(¢));
    for (final PostfixExpression.Operator ¢ : wizard.postfixOperators)
      addIfNecessary("POSTFIX", Vocabulary.mangle(¢));
    for (final Operator ¢ : wizard.infixOperators)
      for (int arity = 2; arity <= maxArity; ++arity)
        addIfNecessary("INFIX", Vocabulary.mangle(¢, arity));
  }

  @Override protected void done(@SuppressWarnings("unused") final String path) {
    dotter.end();
    addMissingKeys();
    if (writer == null)
      writer = new Table(this);
    try (Table t = new Table("rindices")) {
      for (final String category : usage.keySet()) {
        final Map<String, Integer> map = usage.get(category);
        int n = 0;
        int m = 0;
        for (final String key : map.keySet())
          t//
              .col("N", ++n)//
              .col("M", ++m)//
              .col("Category", category)//
              .col("Key", '"' + key + '"')//
              .col("Count", map.get(key)) //
              .nl();
        writer.col(category, rindex(ranks(map)));
        System.err.println("Your output is in: " + t.description());
        addLineToGlobalStatistcs();
      }
    }
  }

  boolean increment(final String category, final String key) {
    return increment(addIfNecessary(category, key), key);
  }

  private String key(final InfixExpression ¢) {
    return key(¢, wizard.arity(¢));
  }

  private String key(final InfixExpression ¢, final int arity) {
    maxArity = Math.max(arity, maxArity);
    return Vocabulary.mangle(¢.getOperator(), arity);
  }

  protected int rExternal() {
    final Map<String, Integer> $ = new LinkedHashMap<>(usage.get("METHOD"));
    for (final String m : defined)
      $.remove(m);
    return rindex(ranks($));
  }

  protected int rInternal() {
    final Map<String, Integer> $ = new LinkedHashMap<>(usage.get("METHOD"));
    for (final String k : new ArrayList<>($.keySet()))
      if (!defined.contains(k))
        $.remove(k);
    return rindex(ranks($));
  }

  protected int rMethod() {
    return rindex(ranks(usage.get("METHOD")));
  }

  @Override public void preVisit(final ASTNode ¢) {
    increment("NODE-TYPE", Vocabulary.mangle(¢.getClass()));
  }

  @Override public boolean visit(final Assignment ¢) {
    return increment("ASSIGNMENT", Vocabulary.mangle(¢));
  }

  @Override public boolean visit(final InfixExpression ¢) {
    return increment("INFIX", key(¢));
  }

  @Override public boolean visit(final MethodDeclaration ¢) {
    return defined.add(Vocabulary.mangle(¢));
  }

  @Override public boolean visit(final MethodInvocation ¢) {
    return increment("METHOD", Vocabulary.mangle(¢));
  }

  @Override public boolean visit(final PostfixExpression ¢) {
    return increment("POSTFIX", Vocabulary.mangle(¢));
  }

  @Override public boolean visit(final PrefixExpression ¢) {
    return increment("PREFIX", Vocabulary.mangle(¢));
  }
}
