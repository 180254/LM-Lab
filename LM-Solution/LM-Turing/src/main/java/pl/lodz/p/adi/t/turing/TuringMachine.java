package pl.lodz.p.adi.t.turing;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class TuringMachine {

    public static final char EMPTY_S = '#';

    private final String alphabet;
    private final TmTransition[/*char*/][/*state*/] transitions;
    private final TmMove firstMove;

    public TuringMachine(String alphabet, TmTransition[][] transitions, TmMove firstMove) {
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.firstMove = firstMove;
    }

    public TmResult execute(String tape) {
        Objects.requireNonNull(tape);

        int currentState = 0;
        int headPos = firstMove == TmMove.RIGHT ? 0 : tape.length() - 1;
        char[] tapeCa = tape.toCharArray();

        do {
            char tapeChar = readChar(tapeCa, headPos);
            int charCode = alphabet.indexOf(tapeChar);
            TmTransition transition = transitions[charCode][currentState];

            if (transition.isPassiveState())
                return new TmResult(currentState, stripTape(tapeCa));

            if (transition.getWriteSymbol() != null)
                tapeCa = updateTape(tapeCa, headPos, transition.getWriteSymbol());
            if (transition.getWriteSymbol() != null && headPos < 0)
                headPos = 0;

            if (transition.getNextState() != null)
                currentState = transition.getNextState();
            if (transition.getTapeMove() != null)
                headPos += transition.getTapeMove().getDiff();

        } while (true);
    }

    private static char readChar(char[] tape, int headPos) {
        if (headPos < 0 || headPos >= tape.length)
            return EMPTY_S;
        else
            return tape[headPos];
    }

    private static char[] updateTape(char[] tape, int headPos, char newChar) {
        if (headPos >= 0 && headPos < tape.length) {
            tape[headPos] = newChar;
            return tape;
        }
        if (headPos < 0) {
            return String.format("%c%s%s",
                    newChar,
                    StringUtils.repeat(EMPTY_S, Math.abs(headPos) - 1),
                    String.valueOf(tape)
            ).toCharArray();
        }
        int diff = headPos - (tape.length - 1);
        return String.format("%s%s%c",
                String.valueOf(tape),
                StringUtils.repeat(EMPTY_S, diff - 1),
                newChar
        ).toCharArray();
    }

    private static String stripTape(char[] tape) {
        return StringUtils.strip(String.valueOf(tape), String.valueOf(EMPTY_S));
    }
}
