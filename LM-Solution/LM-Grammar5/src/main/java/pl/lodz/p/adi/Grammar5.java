package pl.lodz.p.adi;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class Grammar5 {

    private JPanel jPanel;
    private JTextArea inputArea;
    private JTextArea grammarArea;

    private JTextField checkResult;
    private JTextField compileResult;

    private JButton checkButton;
    private JButton compileButton;

    public static void main(String[] args) {
        Grammar5 g5 = new Grammar5();

        JFrame frame = new JFrame("Grammar5");
        frame.setContentPane(g5.jPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        try {
            UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException ignored) {
        }

        g5.checkButton.addActionListener((event) -> g5.doCheck());
        g5.compileButton.addActionListener((event) -> g5.doCompile());


        g5.inputArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    g5.doCheck();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        g5.grammarArea.setText(StringUtils.join(BnfFactory.task5(), "\n"));
        g5.inputArea.setText("(1.2*3)+5-(23.4+3)^3; 8.2/4;");
        g5.doCompile();
        g5.doCheck();
    }

    Pattern grammarPattern = Pattern.compile(".*");

    private synchronized void doCheck() {

        if (Patterns.matches(grammarPattern, inputArea.getText())) {
            checkResult.setText("OK");
            checkResult.setForeground(new Color(0, 85, 0));
        } else {
            checkResult.setText("FAIL");
            checkResult.setForeground(new Color(255, 0, 0));
        }
    }


    private synchronized void doCompile() {
        String[] grammar = grammarArea.getText().split("\r?\n");

        try {
            grammarPattern = BnfToRegexp.compile(grammar);
            compileResult.setText("OK");
            compileResult.setForeground(new Color(0, 85, 0));
        } catch (Exception ex) {
            compileResult.setText("FAIL");
            compileResult.setForeground(new Color(255, 0, 0));
        }
    }


}
