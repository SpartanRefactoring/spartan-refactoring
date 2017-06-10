package il.org.spartan.Leonidas.plugin.GUI.PlaygroundController;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.util.ui.UIUtil;
import il.org.spartan.Leonidas.auxilary_layer.Utils;
import il.org.spartan.Leonidas.plugin.GUI.LeonidasIcon;
import il.org.spartan.Leonidas.plugin.Spartanizer;
import il.org.spartan.Leonidas.plugin.Toolbox;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Anna Belozovsky
 * @since 22/04/2017
 */
public class Playground extends JFrame {
    private JPanel mainPanel;
    private JButton clearButton;
    private JButton spartanizeButton;
    private RSyntaxTextArea inputArea;
    private RSyntaxTextArea outputArea;
    private JPanel textPanel;
    private JPanel buttonPanel;
    private JButton closeButton;
    private JTextPane textPaneInput;
    private JTextPane textPaneOutput;
    private JScrollPane inputScroll;
    private JScrollPane outputScroll;
    private JTextPane inputTextPane;

    private String[] before = {"public class foo{", "public class foo{ public void main(){\n", "public class foo{ public void main(){\nf("};
    private String[] after = {"\n}", "\n}}", ");\n}}"};

    public Playground() {
        super("Spartanizer Playground");
        inputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        outputArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        if (UIUtil.isUnderDarcula()) {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/ui/dark.xml"));
                theme.apply(inputArea);
                theme.apply(outputArea);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO @RoeiRaz do something that makes sense here
            }
        }

        LeonidasIcon.apply(this);
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(600, 600));
        pack();
        setVisible(true);
        outputArea.setEditable(false);
        spartanizeButton.addActionListener(e -> spartanizeButtonClicked());
        clearButton.addActionListener(e -> clearButtonClicked());
        closeButton.addActionListener(e -> closeButtonClicked());
    }

    private void spartanizeButtonClicked() {
        if (inputArea.getText().trim().isEmpty()) {
            return;
        }
        Toolbox.getInstance().playground = true;
        PsiFile file = null;
        final boolean[] worked = {true};
        int i;
        for (i = 0; worked[0] && i < before.length; i++, worked[0] = true) {
            file = PsiFileFactory.getInstance(Utils.getProject())
                    .createFileFromText(JavaLanguage.INSTANCE, before[i] + inputArea.getText() + after[i]);
            file.accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitErrorElement(PsiErrorElement element) {
                    worked[0] = false;
                    super.visitErrorElement(element);
                }
            });
            if (worked[0]) {
                break;
            }
        }
        if (i < before.length && file != null) {
            Spartanizer.spartanizeFileOnePass(file);
            outputArea.setText(fixString(file.getText(), i));
        } else {
            outputArea.setText(inputArea.getText());
        }
        Toolbox.getInstance().playground = false;
    }

    private String fixString(String outputStr, int i) {
        String temp = outputStr.substring(before[i].length());
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(temp.substring(0, temp.length() - after[i].length()).split("\n")));
        return lines.stream().map(line -> line.replaceFirst(" {4}", "")).collect(Collectors.joining("\n"));
    }

    private void clearButtonClicked() {
        outputArea.setText("");
        inputArea.setText("");
    }

    private void closeButtonClicked() {
        dispose();
    }

    public void setInput(String newInput) {
        inputArea.setText(newInput);
    }

    public String getOutput() {
        return outputArea.getText();
    }

    public void doSpartanization() {
        spartanizeButton.doClick();
    }

    public void doClear() {
        clearButton.doClick();
    }

    public void doClose() {
        closeButton.doClick();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setDoubleBuffered(false);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null));
        textPanel = new JPanel();
        textPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(textPanel, gbc);
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setDividerLocation(200);
        splitPane1.setOrientation(0);
        textPanel.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(panel1);
        inputScroll = new JScrollPane();
        panel1.add(inputScroll, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        inputArea = new RSyntaxTextArea();
        inputArea.setBracketMatchingEnabled(false);
        inputArea.setDoubleBuffered(false);
        inputArea.setText("");
        inputScroll.setViewportView(inputArea);
        textPaneInput = new JTextPane();
        textPaneInput.setText("Input:");
        panel1.add(textPaneInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 25), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setRightComponent(panel2);
        outputScroll = new JScrollPane();
        panel2.add(outputScroll, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputArea = new RSyntaxTextArea();
        outputArea.setBracketMatchingEnabled(false);
        outputScroll.setViewportView(outputArea);
        textPaneOutput = new JTextPane();
        textPaneOutput.setText("Output:");
        panel2.add(textPaneOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 25), null, 0, false));
        clearButton = new JButton();
        clearButton.setText("Clear");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(clearButton, gbc);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonPanel, gbc);
        final Spacer spacer1 = new Spacer();
        buttonPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        closeButton = new JButton();
        closeButton.setText("Close");
        buttonPanel.add(closeButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spartanizeButton = new JButton();
        spartanizeButton.setText("Spartanize");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spartanizeButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
