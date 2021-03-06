package il.org.spartan.spartanizer.cmdline.metrics;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;

import fluent.ly.as;
import fluent.ly.note;
import il.org.spartan.CSVStatistics;
import il.org.spartan.collections.FilesGenerator;
import il.org.spartan.spartanizer.ast.factory.makeAST;
import il.org.spartan.spartanizer.ast.navigate.countOf;
import il.org.spartan.spartanizer.ast.nodes.metrics.Metrics;
import il.org.spartan.spartanizer.plugin.TextualTraversals;
import il.org.spartan.spartanizer.tipping.Tip;
import il.org.spartan.spartanizer.traversal.Traversal;
import il.org.spartan.spartanizer.traversal.TraversalImplementation;
import il.org.spartan.utils.FileUtils;

/** Collect basic metrics of files (later on, maybe change to classes)
 * @author Yossi Gil
 * @since Oct 3, 2016 */
enum CollectMetrics {
  ;
  private static final String OUTPUT = "/tmp/test.csv";
  private static final String OUTPUT_Tips = "/tmp/tips.csv";
  private static final CSVStatistics output = init(OUTPUT, "property");
  private static final CSVStatistics Tips = init(OUTPUT_Tips, "tips");

  public static void main(final String[] where) {
    go(where.length != 0 ? where : as.array("."));
    System.err.println("Your output should be here: " + output.close());
  }
  //
  public static Document rewrite(final Traversal t, final CompilationUnit u, final Document $) {
    try {
      t.go(u).rewriteAST($, null).apply($);
      return $;
    } catch (MalformedTreeException | BadLocationException ¢) {
      throw new AssertionError(¢);
    }
  }
  // TODO Yossi Gil: eliminate warning
  private static void collectTips(@SuppressWarnings("unused") final String __, final CompilationUnit before) {
    reportTips(new TraversalImplementation().collectTips(before));
  }
  private static void go(final File f) {
    try {
      // This line is going to give you trouble if you process class by class.
      output.put("File", f.getName());
      Tips.put("File", f.getName());
      go(FileUtils.read(f));
    } catch (final IOException ¢) {
      note.bug(¢);
    }
  }
  private static void go(final String javaCode) {
    output.put("Characters", javaCode.length());
    final CompilationUnit before = (CompilationUnit) makeAST.COMPILATION_UNIT.from(javaCode);
    report("Before-", before);
    collectTips(javaCode, before);
    final CompilationUnit after = spartanize(javaCode);
    assert after != null;
    report("After-", after);
    output.nl();
  }
  private static void go(final String... where) {
    new FilesGenerator(".java").from(where).forEach(CollectMetrics::go);
  }
  private static CSVStatistics init(final String $, final String property) {
    return new CSVStatistics($, property);
  }
  /** fault, what happens if we have many classes in the same file? Also, we do
   * not want to count imports, and package instructions. Write a method that
   * finds all classes, which could be none, at the upper level, and collect on
   * these. Note that you have to print the file name which is common to all
   * classes. Turn this if you like into a documentation
   * @param string */
  private static void report(final String prefix, final CompilationUnit ¢) {
    // TODO Matteo: make sure that the counting does not include comments.
    // Do this by adding stuff to the metrics suite.
    output.put(prefix + "Length", ¢.getLength());
    output.put(prefix + "Count", countOf.nodes(¢));
    output.put(prefix + "Non whites", countOf.nonWhiteCharacters(¢));
    output.put(prefix + "Condensed size", Metrics.condensedSize(¢));
    output.put(prefix + "Lines", countOf.lines(¢));
    output.put(prefix + "Dexterity", Metrics.dexterity(¢));
    output.put(prefix + "Leaves", Metrics.leaves(¢));
    output.put(prefix + "Nodes", Metrics.nodes(¢));
    output.put(prefix + "Internals", Metrics.internals(¢));
    output.put(prefix + "Vocabulary", Metrics.vocabulary(¢));
    output.put(prefix + "Literacy", Metrics.literacy(¢));
    output.put(prefix + "Imports", countOf.imports(¢));
    output.put(prefix + "No Imports", countOf.noimports(¢));
  }
  private static void reportTips(final Iterable<Tip> ¢) {
    for (final Tip $ : ¢) {
      Tips.put("description", $.description);
      Tips.put("from", $.highlight.from);
      Tips.put("to", $.highlight.to);
      Tips.put("linenumber", $.lineNumber);
      Tips.nl();
    }
  }
  private static CompilationUnit spartanize(final String javaCode) {
    final String $ = new TextualTraversals().fixed(javaCode);
    output.put("Characters", $.length());
    return (CompilationUnit) makeAST.COMPILATION_UNIT.from($);
  }
}
