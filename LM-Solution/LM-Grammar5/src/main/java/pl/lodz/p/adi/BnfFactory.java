package pl.lodz.p.adi;

public class BnfFactory {

    public static String[] task5() {
        return new String[]{
                "S              ::= <mExpression>{; <mExpression>}[;]",
                "<mExpression>  ::= <bExpression>{<operator><bExpression>}",
                "<bExpression>  ::= (<expression>)|<expression>",
                "<expression>   ::= [-]<fraction>{<operator><fraction>}",
                "<fraction>     ::= <integer>[.<digit0>{<digit0>}]",
                "<integer>      ::= 0|<digit1>{<digit0>}",
                "<digit0>       ::= 0|1|2|3|4|5|6|7|8|9",
                "<digit1>       ::= 1|2|3|4|5|6|7|8|9",
                "<operator>     ::= +|-|/|*|^"
        };
    }
}
