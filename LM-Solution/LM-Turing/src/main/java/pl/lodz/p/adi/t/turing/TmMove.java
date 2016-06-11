package pl.lodz.p.adi.t.turing;

public enum TmMove {

    LEFT(-1), RIGHT(1), NONE(0);

    private final int diff;

    TmMove(int diff) {
        this.diff = diff;
    }

    public int getDiff() {
        return diff;
    }

    public static TmMove fromCode(String code) {
        switch (code.toUpperCase()) {
            case "L":
                return LEFT;
            case "R":
                return RIGHT;
            case "P":
                return RIGHT;
            case "N":
                return NONE;
            case "-":
                return NONE;
            default:
                throw new EnumConstantNotPresentException(TmMove.class, code);
        }
    }

}
