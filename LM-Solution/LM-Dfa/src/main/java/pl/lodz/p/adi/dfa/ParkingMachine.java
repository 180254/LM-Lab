package pl.lodz.p.adi.dfa;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ParkingMachine {

    private JButton a1Button;
    private JButton a2Button;
    private JButton a5Button;
    private JButton buyButton;
    private JTextArea currentStateArea;
    private JTextArea historyArea;
    private JPanel jPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ParkingMachine");
        frame.setContentPane(new ParkingMachine().jPanel);
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

    // --------------------------------------------------------------------------------------------------

    String alphabet = "125C";
    int[][] transitions = {
            {1, 2, 3, 4, 5, 6, 7, 1, 1, 1},
            {2, 3, 4, 5, 6, 7, 8, 2, 2, 2},
            {5, 6, 7, 8, 8, 8, 8, 5, 5, 5},
            {9, 9, 9, 9, 9, 9, 9, 9, 9, 9}
    };

    int currentState = 0;

    public void executeDfa(char character) {
        blockMachine();

        int charCode = alphabet.indexOf(character);
        currentState = transitions[charCode][currentState];

        onCharacterActions[charCode].run();
        onStateActions[currentState].run();

        enableMachine();
    }

    Runnable[] onCharacterActions = {
            () -> onCharacterAction("1  | Wrzucono 1 zł.\n"),
            () -> onCharacterAction("2  | Wrzucono 2 zł.\n"),
            () -> onCharacterAction("5  | Wrzucono 5 zł.\n"),
            () -> onCharacterAction("C  | Anulowano zakup.\n")
    };

    Runnable[] onStateActions = {
            () -> onStateAction("0  | Pobrano 0 zł.\n"),
            () -> onStateAction("1  | Pobrano 1 zł.\n"),
            () -> onStateAction("2  | Pobrano 2 zł.\n"),
            () -> onStateAction("3  | Pobrano 3 zł.\n"),
            () -> onStateAction("4  | Pobrano 4 zł.\n"),
            () -> onStateAction("5  | Pobrano 5 zł.\n"),
            () -> onStateAction("6  | Pobrano 6 zł.\n"),
            () -> onStateAction("7  | Pobrano 0 zł. | Pobrano opłatę. | Parking opłacony.\n"),
            () -> onStateAction("8  | Pobrano 0 zł. | Zwrot monet.    | Przekroczono koszt.\n"),
            () -> onStateAction("9  | Pobrano 0 zł. | Zwrot monet     | Zakup anulowany.\n"),
    };

    // --------------------------------------------------------------------------------------------------


    public ParkingMachine() {
        DefaultCaret historyCaret = (DefaultCaret) historyArea.getCaret();
        historyCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        a1Button.addActionListener(e -> a1ButtonClicked());
        a2Button.addActionListener(e -> a2ButtonClicked());
        a5Button.addActionListener(e -> a5ButtonClicked());
        buyButton.addActionListener(e -> cancelButtonClicked());
    }

    // --------------------------------------------------------------------------------------------------

    private void onCharacterAction(String description) {
        description = "Wykonana akcja: " + description;

        resetCurrentState();
        appendToCurrentState(description);
        appendToHistory(description);
    }

    private void onStateAction(String description) {
        description = "Aktualny stan:  " + description;

        appendToCurrentState(description);
        appendToHistory(description);
    }

    private void resetCurrentState() {
        currentStateArea.setText("");
    }

    private void appendToCurrentState(String value) {
        currentStateArea.append(value);
    }

    private void appendToHistory(String value) {
        historyArea.append(value);
    }

    private void enableMachine() {
        setMachineState(true);
    }

    private void blockMachine() {
        setMachineState(false);
    }

    private void setMachineState(boolean state) {
        a1Button.setEnabled(state);
        a2Button.setEnabled(state);
        a5Button.setEnabled(state);
        buyButton.setEnabled(state);
    }

    // --------------------------------------------------------------------------------------------------

    private void a1ButtonClicked() {
        executeDfa('1');
    }

    private void a2ButtonClicked() {
        executeDfa('2');
    }

    private void a5ButtonClicked() {
        executeDfa('5');
    }

    private void cancelButtonClicked() {
        executeDfa('C');
    }
}
