package il.org.spartan.spartanizer.cmdline.report;

import static il.org.spartan.tide.*;

import java.io.*;
import java.util.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.*;
import il.org.spartan.plugin.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.cmdline.*;
import il.org.spartan.spartanizer.cmdline.report.ReportGenerator.*;
import il.org.spartan.spartanizer.utils.*;

/** Configurable Report that uses {@link Listener.S}
 * @author Yossi Gil
 * @author Matteo Orru'
 * @since Nov 14, 2016 */
public interface ConfigurableReport {
  class Settings extends Listener.S {
    private static final long serialVersionUID = 1L;
    String outputFolder = "/tmp/"; // default modifier
    String inputFolder; // default modifier
    private static String reportFileName;
    private static String header;
    CSVStatistics report;
    private static ArrayList<ASTNode> inputList = new ArrayList<>();
    private static ArrayList<ASTNode> outputList = new ArrayList<>();

    public static void addInput(final ASTNode input) {
      getInputList().add(input);
    }

    public static void addOutput(final ASTNode output) {
      getOutputList().add(output);
    }

    public CSVStatistics report() {
      return report;
    }

    @SuppressWarnings("rawtypes") public static NamedFunction[] functions(final String id) {
      return as.array(m("length" + id, λ -> (λ + "").length()), m("essence" + id, λ -> Essence.of(λ + "").length()),
          m("tokens" + id, λ -> metrics.tokens(λ + "")), m("nodes" + id, count::nodes), m("body" + id, metrics::bodySize),
          m("methodDeclaration" + id, λ -> az.methodDeclaration(λ) == null ? -1 : extract.statements(az.methodDeclaration(λ).getBody()).size()),
          m("tide" + id, λ -> clean(λ + "").length()));//
    }

    static NamedFunction<ASTNode> m(final String name, final ToInt<ASTNode> f) {
      return new NamedFunction<>(name, f);
    }

    private ASTNode input;
    private ASTNode output;
    public boolean robustMode;

    public ASTNode getInput() {
      return input;
    }

    public ASTNode getOutput() {
      return output;
    }

    public String getOutputFolder() {
      return outputFolder;
    }

    public void setOutputFolder(final String outputFolder) {
      this.outputFolder = outputFolder;
    }

    public String getInputFolder() {
      return inputFolder;
    }

    public void setInputFolder(final String inputFolder) {
      this.inputFolder = inputFolder;
    }

    public void setReport(final String reportFilename, final String header) {
      try {
        report = new CSVStatistics(reportFilename, header);
      } catch (final IOException ¢) {
        monitor.infoIOException(¢, header);
      }
    }

    static String getHeader() {
      return header;
    }

    /** Action provide services
     * @see #go()
     * @author Yossi Gil
     * @author Matteo Orru' */
    @SuppressWarnings("TooBroadScope")
    public class Action extends Settings {
      /** real serialVersionUID comes much later in production code */
      private static final long serialVersionUID = 1L;

      @SuppressWarnings("boxing") int go() {
        // listeners().push("Initializing the " + getFileName() + " report.");
        if (Settings.this.robustMode) {
          listeners().pop("we dare do nothing in robust mode");
          return 0;
        }
        // listeners().tick("generate summary file name");
        summaryFileName();
        for (int i = 0; i < getInputList().size(); ++i) {
          // write
          // listeners().tick("writing basic data");
          name(getInputList().get(i));
          // write
          // listeners().tick("writing metrics");
          write(getInputList().get(i), getOutputList().get(i));
          // write
          // listeners().tick("writing differences");
          write(getInputList().get(i), getOutputList().get(i), "Δ ", (n1, n2) -> (n1 - n2));
          // write
          // listeners().tick("writing delta");
          write(getInputList().get(i), getOutputList().get(i), "δ ", system::d);
          // write
          // listeners().tick("writing perc");
          writePerc(getInputList().get(i), getOutputList().get(i), "δ ");
          // write
          // listeners().tick("writing ratio");
          // not yet
          report.nl();
        }
        // listeners().pop("exhausted");
        return defaultValue();
      }

      private void name(final ASTNode i) {
        report().put("name", extract.name(i));
        report().put("category", extract.category(i));
      }

      private void summaryFileName() {
        report().summaryFileName();
      }

      public void close() {
        report().close();
      }

      private int defaultValue() {
        return hashCode();
      }

      // running report
      @SuppressWarnings({ "unchecked", "rawtypes" }) private void write(final ASTNode i, final ASTNode n) {
        for (final NamedFunction ¢ : ReportGenerator.Util.functions("")) {
          report().put(¢.name() + "1", ¢.function().run(i));
          report().put(¢.name() + "2", ¢.function().run(n));
        }
      }

      @SuppressWarnings({ "boxing", "unchecked" }) private void write(final ASTNode i, final ASTNode n, final String id,
          final BiFunction<Integer, Integer> bf) {
        if (bf == null && id == null) {
          write(i, n);
          return;
        }
        assert bf != null;
        assert id != null;
        as.list(ReportGenerator.Util.functions("")).forEach(λ -> report().put(id + λ.name(), bf.apply(λ.function().run(i), λ.function().run(n))));
      }

      @SuppressWarnings({ "unchecked", "rawtypes" }) private void writePerc(final ASTNode n1, final ASTNode n2, final String id) {
        String a; // TODO Matteo: to be converted to double or float? -- Matteo
        for (final NamedFunction ¢ : ReportGenerator.Util.functions("")) {
          a = system.p(¢.function().run(n1), ¢.function().run(n2));
          report().put(id + ¢.name() + " %", a);
        }
      }

      public void initialize() {
        try {
          report = new CSVStatistics(getFileName(), getHeader());
        } catch (final IOException ¢) {
          ¢.printStackTrace();
        }
      }
    }

    public Action getAction() {
      return new Action();
    }

    public static void setHeader(final String ¢) {
      header = ¢;
    }

    public static void setFileName(final String ¢) {
      reportFileName = ¢;
    }

    public static String getFileName() {
      return reportFileName;
    }

    public void setInput(final ASTNode ¢) {
      input = ¢;
    }

    public void setOutput(final ASTNode ¢) {
      output = ¢;
    }

    public static List<ASTNode> getInputList() {
      return inputList;
    }

    public static void setInputList(final ArrayList<ASTNode> inputList) {
      Settings.inputList = inputList;
    }

    public static List<ASTNode> getOutputList() {
      return outputList;
    }

    public static void setOutputList(final ArrayList<ASTNode> outputList) {
      Settings.outputList = outputList;
    }
  }
}
