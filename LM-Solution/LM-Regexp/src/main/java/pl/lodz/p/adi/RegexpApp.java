package pl.lodz.p.adi;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.util.regex.Pattern;


public class RegexpApp {

    public Pattern[] patterns = null;
    private JTextArea inputArea;
    private JComboBox<String> selectCombo;
    private JButton checkButton;
    private JTextArea resultArea;
    private JPanel jPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegexpApp");
        frame.setContentPane(new RegexpApp().jPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException ignored) {
        }
    }

    public RegexpApp() {
        selectCombo.addItem("Adres IP z maską podsieci (rozdzielone spacją)");
        selectCombo.addItem("Adres e-mail");
        selectCombo.addItem("Dodawanie liczb całkowitych");
        selectCombo.addItem("Dodawanie liczb zespolonych");
        selectCombo.addItem("Wybrane dwa tagi HTML: td i span");
        checkButton.addActionListener((event) -> checkButtonClicked());

        patterns = new Pattern[5];

        String ip_regexp = "0*(25[025]|2[024]\\d|1?\\d\\d?)(\\.0*(25[025]|2[024]\\d|1?\\d\\d?)){3}";
        String mask_onlyOnes = "(255\\.)";
        String mask_leadingOnes = "(255|254|252|248|240|224|192|128|0+)";
        String mask_regexp = "((" + mask_onlyOnes + "{3}" + mask_leadingOnes + ")|" +
                "(" + mask_onlyOnes + "{2}" + mask_leadingOnes + "\\.0+)|" +
                "(" + mask_onlyOnes + mask_leadingOnes + "(\\.0+){2})|" +
                "(" + mask_leadingOnes + "(\\.0+){3}))";
        patterns[0] = Pattern.compile("^" + ip_regexp + " " + mask_regexp + "$");

        patterns[1] = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-.]*)\\.[a-z]+$");

        patterns[2] = Pattern.compile("^\\d+(?:\\s*\\+\\s*\\d+)*$");
        patterns[3] = Pattern.compile("^\\s*\\d+(?:\\.\\d+)*[ij]?+(?:\\s*\\+\\s*\\d+(?:\\.\\d+)*[ij]?+)*\\s*$");
        patterns[4] = Pattern.compile("^<td\\s*" +
                "( colspan=\"\\d+\")?\\s*" +
                "( rowspan=\"\\d+\")?\\s*" +
                "( headers=\"[a-z][a-z0-9]*(,[a-z][a-z0-9]*)*\")?" +
                "\\s*>\\s*" +
                "(\\s*<span>([^<>]*)<\\/span>\\s*)*" +
                "\\s*<\\/td>$");


    }

    private void checkButtonClicked() {
        String[] inputs = inputArea.getText().split("\r?\n");
        Pattern pattern = patterns[selectCombo.getSelectedIndex()];

        resultArea.setText("");
        for (String input : inputs) {
            if (input.length() > 0) {
                if (pattern.matcher(input).matches()) {
                    resultArea.append(input + " -> Ok\n");
                } else {
                    resultArea.append(input + " -> Błąd\n");
                }
            }
        }
    }
}
