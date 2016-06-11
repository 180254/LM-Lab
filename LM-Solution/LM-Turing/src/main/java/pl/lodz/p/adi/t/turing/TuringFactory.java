package pl.lodz.p.adi.t.turing;

import static pl.lodz.p.adi.t.turing.TmMove.RIGHT;
import static pl.lodz.p.adi.t.turing.TmTransition.t;

public class TuringFactory {


    public static TuringMachine parkingMachine() {
        String alphabet = "#125";

        // last state = 17 - parking ticket given
        // last state = 18 - parking ticket not given
        // tape - change
        TmTransition[][] transitions = {
                { // empty
                        t("18,-,-"), t("7,#,-"), t("8,#,-"), t("9,#,-"), t("10,#,-"), t("11,#,-"), t("12,#,-"),
                        t("18,1,-"), t("18,2,-"), t("7,2,L"), t("8,2,L"), t("18,5,-"), t("7,5,L"), t("17,1,-"),
                        t("17,2,-"), t("13,2,L"), t("14,2,L"), t("-,-,-"), t("-,-,-")

                },
                { // 1
                        t("1,#,R"), t("2,#,R"), t("3,#,R"), t("4,#,R"), t("5,#,R"), t("6,#,R"), t("17,#,-"),
                        t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"),
                        t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-")

                },
                { // 2

                        t("2,#,R"), t("3,#,R"), t("4,#,R"), t("5,#,R"), t("6,#,R"), t("17,#,-"), t("13,#,-"),
                        t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"),
                        t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-")

                },
                { // 5
                        t("5,#,R"), t("6,#,R"), t("17,#,-"), t("13,#,-"), t("14,#,-"), t("15,#,-"), t("16,#,-"),
                        t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"),
                        t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-"), t("-,-,-")
                },
        };

        TmMove firstMove = RIGHT;

        return new TuringMachine(alphabet, transitions, firstMove);
    }

}
