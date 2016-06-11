package pl.lodz.p.adi.t.turing;

import java.util.Objects;

public class TmTransition {

    private final Character writeSymbol;
    private final TmMove tapeMove;
    private final Integer nextState;

    private TmTransition(Integer nextState, Character writeSymbol, TmMove tapeMove) {
        if (writeSymbol != null || tapeMove != null)
            Objects.requireNonNull(nextState);

        this.writeSymbol = writeSymbol;
        this.tapeMove = tapeMove;
        this.nextState = nextState;
    }

    public static TmTransition t(Integer nextState, Character writeSymbol, TmMove tapeMove) {
        return new TmTransition(nextState, writeSymbol, tapeMove);
    }

    /**
     * -,-,-
     * 1,#,L
     * 2,s,P
     * 3,-,-
     */
    public static TmTransition t(String str) {
        Objects.requireNonNull(str);

        if (str.equals("-,-,-"))
            return p();

        String[] split = str.split(",");
        int nextState = Integer.parseInt(split[0]);
        Character writeSymbol = split[1].equals("-") ? null : split[1].charAt(0);
        TmMove tapeMove = TmMove.fromCode(split[2]);

        return new TmTransition(nextState, writeSymbol, tapeMove);
    }

    public static TmTransition p() {
        return new TmTransition(null, null, null);
    }

    public Integer getNextState() {
        return nextState;
    }

    public Character getWriteSymbol() {
        return writeSymbol;
    }

    public TmMove getTapeMove() {
        return tapeMove;
    }

    public boolean isPassiveState() {
        return nextState == null
                && writeSymbol == null
                && (tapeMove == null || tapeMove == TmMove.NONE);
    }
}
