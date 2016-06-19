package pl.lodz.p.adi;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Grammar6 {

    private JTextArea inputArea;
    private JTextArea resultArea;
    private JPanel jPanel;

    public static void main(String[] args) {
        Grammar6 g6 = new Grammar6();

        try {
            UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException ignored) {
        }

        JFrame frame = new JFrame("Grammar6");
        frame.setContentPane(g6.jPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public Grammar6() {
        inputArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    doCheck();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        inputArea.setText("(1.2*3):5-(23.4+3)^(3*(0.02-(1.2*2+4)-1)); 8.2/4;");
    }


    private synchronized void doCheck() {
        G6Validator g6v = new G6Validator(inputArea.getText());

        if (g6v.checkS()) {
            resultArea.setText("OK");
            resultArea.setForeground(new Color(0, 85, 0));
        } else {
            resultArea.setText("FAIL");
            resultArea.setForeground(new Color(255, 0, 0));
        }
    }
}
