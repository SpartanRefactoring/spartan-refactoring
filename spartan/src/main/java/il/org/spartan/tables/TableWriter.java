package il.org.spartan.tables;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import fluent.ly.separate;
import il.org.spartan.Separator;

/** TODO Yossi Gil Document Classn
 *
 * @author Yossi Gil
 * @since 2016-12-25 */
public class TableWriter implements Closeable {
  /** Create a new instance, writing into a given named file
   *
   * @param basePath the name of the output file
   * @throws IOException */
  public TableWriter(final TableRenderer renderer, final String basePath) throws IOException {
    this.renderer = renderer;
    fileName = basePath.replaceAll("\\.[a-z0-9]*$", "") + "." + renderer.extension();
    file = new File(fileName);
    writer = new FileWriter(file);
    write(renderer.beforeTable());
  }

  public void write(final String s) {
    try {
      writer.write(s);
      writer.flush();
    } catch (final IOException ¢) {
      throw new RuntimeException(¢);
    }
  }

  public final File file;
  /** The name of the file into which records are written. */
  public final String fileName;
  public final OutputStreamWriter writer;
  public final TableRenderer renderer;
  private boolean shouldPrintHeader = true;
  private boolean footerPrinted;

  @Override public void close() {
    try {
      if (footerPrinted)
        write(renderer.afterFooter());
      write(renderer.afterTable());
      writer.close();
    } catch (final IOException ¢) {
      throw new RuntimeException(¢);
    }
  }

  public void write(final Map<String, Object> ¢) {
    if (shouldPrintHeader) {
      shouldPrintHeader = false;
      writeHeader(¢);
    }
    writeData(¢);
  }

  public void writeFooter(final Map<String, Object> ¢) {
    if (!footerPrinted) {
      write(renderer.beforeFooter());
      footerPrinted = true;
    }
    write(renderer.footerBegin() + separate.these(¢.values()).by(renderer.footerSeparator()) + renderer.footerEnd());
  }

  private void writeData(final Map<String, Object> ¢) {
    write(renderer.recordBegin() + separate.these(¢.values()).by(renderer.recordSeparator()) + renderer.recordEnd());
  }

  private void writeHeader(final Map<String, Object> ¢) {
    renderer.setHeaderCount(¢.size());
    write(renderer.beforeHeader() + //
        renderer.headerLineBegin() + writeHeaderInner(¢) + renderer.headerLineEnd() + //
        renderer.afterHeader());
  }

  private String writeHeaderInner(final Map<String, Object> m) {
    final Separator s = new Separator(renderer.headerSeparator());
    final StringBuilder $ = new StringBuilder();
    m.keySet().forEach(λ -> $.append(s).append(λ != null ? λ : renderer.nil()));
    return $ + "";
  }
}
