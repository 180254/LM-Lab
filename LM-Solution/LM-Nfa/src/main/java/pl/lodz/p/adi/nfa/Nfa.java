package pl.lodz.p.adi.nfa;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Nfa {

    private JPanel jPanel;
    private JTextArea resultArea1;
    private JTextArea resultArea2;
    private JFileChooser fileChooser;
    private JTextField stateField;

    public Nfa() {
        fileChooser.addActionListener(this::fileSelected);
    }

    private void fileSelected(ActionEvent e) {
        File selectedFile;
        String selectedName = "?";
        String selectedPath;
        try {
            resultArea1.setText("");
            resultArea2.setText("");

            selectedFile = fileChooser.getSelectedFile();
            selectedName = selectedFile.getName();
            selectedPath = selectedFile.getAbsolutePath();
            String content = new String(Files.readAllBytes(Paths.get(selectedPath)));

            if (!checkAlphabet(content)) {
                stateField.setText("Bledny plik! Zly alfabet " + selectedName);
                return;
            }

            for (String line : content.split("\n")) {
                for (String word : line.split("#")) {
                    String word2 = word.trim();
                    JTextArea resultArea = check(word2) ? resultArea1 : resultArea2;
                    if (!resultArea.getText().isEmpty()) {
                        resultArea.append("\n");
                    }
                    resultArea.append(word);
                }
            }

            stateField.setText("Zakonczono z sukcesem. Przeczytano: " + selectedName);
        } catch (IOException | RuntimeException ex) {
            stateField.setText("Bledny plik! Nie udalo sie odczytac: " + selectedName);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Nfa");
        frame.setContentPane(new Nfa().jPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addAll(Collection<Integer> col, int[] values) {
        for (int value : values) {
            col.add(value);
        }
    }

    private boolean check(String str) {
        String alphabet = "1234567890";
        int initState = 0;
        int[] acceptedStates = {11};
        int[][/*char*/][/*state*/] transitions = {
                {{0, 1}, {11}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {11}},
                {{0, 2}, {}, {11}, {}, {}, {}, {}, {}, {}, {}, {}, {11}},
                {{0, 3}, {}, {}, {11}, {}, {}, {}, {}, {}, {}, {}, {11}},
                {{0, 4}, {}, {}, {}, {11}, {}, {}, {}, {}, {}, {}, {11}},
                {{0, 5}, {}, {}, {}, {}, {11}, {}, {}, {}, {}, {}, {11}},
                {{0, 6}, {}, {}, {}, {}, {}, {11}, {}, {}, {}, {}, {11}},
                {{0, 7}, {}, {}, {}, {}, {}, {}, {11}, {}, {}, {}, {11}},
                {{0, 8}, {}, {}, {}, {}, {}, {}, {}, {11}, {}, {}, {11}},
                {{0, 9}, {}, {}, {}, {}, {}, {}, {}, {}, {11}, {}, {11}},
                {{0, 10}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {11}, {11}},
        };

        Set<Integer> currentStates = new HashSet<>();
        currentStates.add(initState);

        for (char character : str.toCharArray()) {
            int charCode = alphabet.indexOf(character);

            Set<Integer> newCurrentStates = new HashSet<>();
            for (Integer currentState : currentStates) {
                int[] ncs = transitions[charCode][currentState];
                addAll(newCurrentStates, ncs);
            }

            currentStates = newCurrentStates;
        }

        return IntStream.of(acceptedStates).anyMatch(currentStates::contains);
    }

    private boolean checkAlphabet(String str) {
        // stan 0 = poprawny
        // stan 1 = niepoprawny
        String alphabet = "1234567890\r\n#qwertyuiopasdfghjklzxcvbnm";
        int[][] transitions = {
                // 1-0
                {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1},
                // \r\n#
                {0, 1}, {0, 1}, {0, 1},

                // letters
                {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
                {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}
        };

        int currentState = 0;

        for (char c : str.toCharArray()) {
            int charCode = alphabet.indexOf(c);
            currentState = transitions[charCode][currentState];
        }

        return currentState == 0;
    }
}
