package pl.lodz.p.adi;

import com.google.common.primitives.Chars;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SimplifiableIfStatement")
public class G6Validator {

    List<Character> chars;
    int index;

    public G6Validator(String str) {
        chars = new ArrayList<>(Chars.asList(str.toCharArray()));
    }

    public boolean checkS() {
        index = -1;

        return checkW()
                && checkT(';')
                && checkT(' ')
                && checkZ()
                && index == chars.size() - 1;
    }

    private boolean checkZ() {
        int backup = index;

        if (checkW()) {
            return checkT(';')
                    && checkT(' ')
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
        return index < chars.size()
                && "0123456789".indexOf(chars.get(index)) != -1;
    }

    private boolean checkO() {
        index++;
        return index < chars.size()
                && "*:+-/^".indexOf(chars.get(index)) != -1;
    }

    private boolean checkT(char ch) {
        index++;
        return index < chars.size()
                && chars.get(index) == ch;
    }
}
