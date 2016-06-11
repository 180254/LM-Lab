package pl.lodz.p.adi.t.turing;

public class TmResult {

    private final int state;
    private final String tape;

    public TmResult(int state, String tape) {
        this.state = state;
        this.tape = tape;
    }

    public int getState() {
        return state;
    }

    public String getTape() {
        return tape;
    }

    @Override
    public String toString() {
        return String.format("TmResult{state=%d, tape=%s}", state, tape);
    }
}
