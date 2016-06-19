package pl.lodz.p.adi;

@SuppressWarnings("SimplifiableIfStatement")
public class G6Validator {

    private char[] chars;
    private int index;

    public G6Validator(String str) {
        chars = str.toCharArray();
    }

    public boolean checkS() {
        index = -1;

        return checkW()
                && checkT(';')
                && checkZ()
                && end();
    }

    private boolean checkZ() {
        int backup = index;

        if (checkT(' ')) {
            return checkW()
                    && checkT(';')
                    && checkZ();
        } else {
            index = backup;
            return true;
        }
    }

    private boolean checkW() {
        return checkP()
                && checkWp();
    }

    private boolean checkWp() {
        int backup = index;

        if (checkO()) {
            return checkW();
        } else {
            index = backup;
            return true;
        }
    }

    private boolean checkP() {
        int backup = index;

        if (checkR()) {
            return true;
        } else {
            index = backup;
            return checkT('(')
                    && checkW()
                    && checkT(')');
        }

    }

    private boolean checkR() {
        return checkL()
                && checkRp();

    }

    private boolean checkRp() {
        int backup = index;

        if (checkT('.')) {
            return checkL();
        } else {
            index = backup;
            return true;
        }
    }

    private boolean checkL() {
        return checkC()
                && checkLp();

    }

    private boolean checkLp() {
        int backup = index;

        if (checkL()) {
            return true;
        } else {
            index = backup;
            return true;
        }
    }

    private boolean checkC() {
        index++;
        return hasChar()
                && "0123456789".indexOf(chars[index]) != -1;
    }

    private boolean checkO() {
        index++;
        return hasChar()
                && "*:+-/^".indexOf(chars[index]) != -1;
    }

    private boolean checkT(char ch) {
        index++;
        return hasChar()
                && chars[index] == ch;
    }

    private boolean hasChar() {
        return index < chars.length;
    }

    private boolean end() {
        return index == chars.length - 1;
    }
}
