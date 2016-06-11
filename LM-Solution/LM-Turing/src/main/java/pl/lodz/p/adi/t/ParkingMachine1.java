package pl.lodz.p.adi.t;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import pl.lodz.p.adi.t.turing.TmResult;
import pl.lodz.p.adi.t.turing.TuringFactory;
import pl.lodz.p.adi.t.turing.TuringMachine;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ParkingMachine1 {

    private final TuringMachine parkingMachine = TuringFactory.parkingMachine();

    private JPanel jPanel;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a5Button;
    private JButton buyButton;

    private JTextArea inputTapeArea;
    private JTextArea outputTapeArea;
    private JTextArea stateArea;


    public static void main(String[] args) {
        JFrame frame = new JFrame("ParkingMachine1");
        frame.setContentPane(new ParkingMachine1().jPanel);
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

    public ParkingMachine1() {
        inputTapeArea.setBorder(
                BorderFactory.createCompoundBorder(
                        inputTapeArea.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
        outputTapeArea.setBorder(
                BorderFactory.createCompoundBorder(
                        outputTapeArea.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
        stateArea.setBorder(
                BorderFactory.createCompoundBorder(
                        stateArea.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );

        DefaultCaret historyCaret = (DefaultCaret) stateArea.getCaret();
        historyCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        a1Button.addActionListener(e -> a1ButtonClicked());
        a2Button.addActionListener(e -> a2ButtonClicked());
        a5Button.addActionListener(e -> a5ButtonClicked());
        buyButton.addActionListener(e -> buyButtonClicked());
    }


    // --------------------------------------------------------------------------------------------------

    private void a1ButtonClicked() {
        inputTapeArea.append("1 ");
    }

    private void a2ButtonClicked() {
        inputTapeArea.append("2 ");
    }

    private void a5ButtonClicked() {
        inputTapeArea.append("5 ");
    }

    private void buyButtonClicked() {
        String tape = inputTapeArea.getText().replace(" ", "");
        TmResult result = parkingMachine.execute(tape);
        String newTape = result.getTape().replaceAll(".(?=.)", "$0 ");

        outputTapeArea.setText(newTape);
        inputTapeArea.setText("");

        switch (result.getState()) {
            case 17:
                stateArea.setText("Bilet wydano. Proszę odebrać resztę z taśmy.");
                break;
            case 18:
                stateArea.setText("Za mało pieniędzy. Gotówka zwrócona na taśmie.");
                break;
            default:
                throw new RuntimeException("?!?!?!?");
        }
    }
}
