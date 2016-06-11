package pl.lodz.p.adi;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class BTRTest {

    @Test
    public void test1() {
        String[] grammar = new String[]{
                "S ::= W ;{ W ;}",
                "W ::= L O L{ O L}",
                "L ::= C{C}",
                "C ::= 1|2|3|4|5|6|7|8|9|0",
                "O ::= *|/|+|-|^"
        };
        Pattern patternX = BnfToRegexp.compile(grammar);
        Assert.assertTrue(patternX.matcher("12 + 2 ; 3 * 8 ^ 12 - 2 / 3 ;").matches());
        Assert.assertFalse(patternX.matcher("12 + 2 ; 3 * 8 12 - 2 / 3 ;").matches());

    }

    @Test
    public void test2() {
        String[] grammar = new String[]{
                "S ::= A[.L][ eA]",
                "A ::= [-]L",
                "L ::= C{C}",
                "C ::= 1|2|3|4|5|6|7|8|9|0",
        };
        Pattern patternY = BnfToRegexp.compile(grammar);
        Assert.assertTrue(patternY.matcher("-15.6 e-3").matches());
        Assert.assertFalse(patternY.matcher("-15. e-3").matches());
    }

    @Test
    public void test3() {
        String[] grammar = BnfFactory.task5();

        Pattern pattern = BnfToRegexp.compile(grammar);
        System.out.println(pattern.matcher("12+2*9;").matches());
        System.out.println(pattern.matcher("3*8^12-2/3").matches());
        System.out.println(pattern.matcher("(1.2*3)+5-(23.4+3)^3;").matches());
        System.out.println(pattern.matcher("8.2/4;").matches());
        System.out.println(pattern.matcher("8.2/4+(2*3);").matches());
        System.out.println(pattern.matcher("833.252434/4.33+(23.3*3444.0)^0.1;").matches());
        System.out.println(pattern.matcher("1^0.1").matches());
        System.out.println(pattern.matcher("0.1").matches());
        System.out.println(pattern.matcher("0.0;").matches());
        System.out.println(pattern.matcher("1.1;").matches());
        System.out.println(pattern.matcher("8.2/0;").matches());

        String asdf = "(1.2*3)+5-(23.4+3)^3; 8.2/4+2; ";

        System.out.println(pattern.matcher(StringUtils.repeat(asdf, 3).trim()).matches());
        System.out.println(pattern.matcher(StringUtils.repeat(asdf, 10).trim()).matches());

        System.out.println("-----------");

        System.out.println(pattern.matcher("(1.2*3)+5-(23.4+3)^").matches());
        System.out.println(pattern.matcher("(1.2*3)+5-(23.4+3)^3+(").matches());
        System.out.println(pattern.matcher("(1.2*3)+5-(23.4.4+3)^3").matches());
        System.out.println(pattern.matcher("(1.2*3)+5-(23.4+3)3").matches());
        System.out.println(pattern.matcher("(1.2*3)+5(23.4+3)^3").matches());
        System.out.println(pattern.matcher("(1.2*3+5-(23.4+3)^3").matches());
        System.out.println(pattern.matcher("8./4").matches());
        System.out.println(pattern.matcher("8.2/").matches());
        System.out.println(pattern.matcher("8.2/2.").matches());
        System.out.println(pattern.matcher("00.0").matches());

        System.out.println(pattern.matcher(StringUtils.repeat(asdf, 3)).matches());
        System.out.println(pattern.matcher(StringUtils.repeat(asdf, 10)).matches());




    }

//    String[] grammar = new String[]{
//            "S ::= <mu>{; <mu>}",
//            "<mu> ::= <be>{<op><be>}",
//            "<be> ::= (<ex>)|<ex>",
//            "<ex> ::= <fr>{<op><fr>}",
//            "<fr> ::= <l1>{<l0>}|<l1>{<l0>}.<l0>{<l0>}",
//            "<l0> ::= 1|2|3|4|5|6|7|8|9|0",
//            "<l1> ::= 1|2|3|4|5|6|7|8|9",
//            "<op> ::= +|-|/|*|^"
//    };
//    String[] grammar2 = new String[]{
//            "<S> ::= <W>; <Z>",
//            "<Z> ::= <W>; <Z>|\0",
//            "<W> ::= <P><W'>",
//            "<W'>::= <O><W>|\0",
//            "<P> ::= <R>|(<W>)",
//            "<R> ::= <L>|<L>.<L>",
//            "<L> ::= <C><L'>",
//            "<L'>::= <L>|\0",
//            "<C> ::= 0|1|2|3|4|5|6|7|8|9",
//            "<O> ::= *|:|+|-|^"
//    };
//


}
