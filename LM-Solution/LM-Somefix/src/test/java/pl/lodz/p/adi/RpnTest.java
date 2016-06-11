package pl.lodz.p.adi;

import org.junit.Assert;
import org.junit.Test;

public class RpnTest {

    @Test
    public void infix_to_postfix_test1() {
        Infix infix = new Infix("(2+3.55)*5");
        Postfix postfix = infix.toPostfix();
        String expression = postfix.getExpression();

        Assert.assertEquals("2 3.55 + 5 *", expression);
    }

    @Test
    public void infix_to_postfix_test2() {
        Infix infix = new Infix("((2+7)/3+(14-3)*4)/2");
        Postfix postfix = infix.toPostfix();
        String expression = postfix.getExpression();

        Assert.assertEquals("2 7 + 3 / 14 3 - 4 * + 2 /", expression);
    }

    @Test
    public void infix_to_postfix_test3() {
        Infix infix = new Infix("12 + a * (b * c + d / e)");
        Postfix postfix = infix.toPostfix();
        String expression = postfix.getExpression();

        Assert.assertEquals("12 a b c * d e / + * +", expression);
    }

    @Test
    public void infix_to_postfix_test4() {
        Infix infix = new Infix("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3");
        Postfix postfix = infix.toPostfix();
        String expression = postfix.getExpression();

        Assert.assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +", expression);
    }

    @Test
    public void postfix_to_infix_test1() {
        Postfix postfix = new Postfix("abc-+de-fg-h+/*");
        Infix infix = postfix.toInfix();
        String expression = infix.getExpression();

        Assert.assertEquals("((a+(b-c))*((d-e)/((f-g)+h)))", expression);
    }

    @Test
    public void postfix_calc_test1() {
        Postfix postfix = new Postfix("2 3 + 5 *");
        Assert.assertEquals(25, postfix.calc(), 1e-10);
    }

    @Test
    public void postfix_calc_test2() {
        Postfix postfix = new Postfix("2 7 + 3 / 14 3 - 4 * + 2 /");
        Assert.assertEquals(23.5, postfix.calc(), 1e-10);
    }

    @Test
    public void postfix_calc_test3() {
        Postfix postfix = new Postfix("12 2 3 4 * 10 5 / + * +");
        Assert.assertEquals(40, postfix.calc(), 1e-10);
    }

    @Test
    public void postfix_calc_test4() {
        Postfix postfix = new Postfix("3 4 2 * 1 5 - 2 3 ^ ^ / +");
        Assert.assertEquals(3.00012207031, postfix.calc(), 1e-10);
    }

    @Test
    public void postfix_calc_test5() {
        Infix infix = new Infix("3 % 4 * 2 % ( 1 - 5 ) ^ 2 ^ 3%8");
        Assert.assertEquals(6, infix.calc(), 1e-10);
    }

    @Test
    public void conv_test1() {
        Infix infix = new Infix("3 % 4 * 2 % ( 1 - 5 ) ^ 2 ^ 3%8");
        Postfix postfix = infix.toPostfix();
        Infix infix1 = postfix.toInfix();

        Assert.assertEquals("((((3%4)*2)%((1-5)^(2^3)))%8)", infix1.getExpression());
    }

    @Test
    public void conv_test2() {
        Postfix postfix = new Postfix("2 7 + 3 / 14 3 - 4 * + 2 /");
        Infix infix = postfix.toInfix();
        Postfix postfix1 = infix.toPostfix();

        Assert.assertEquals(postfix.getExpression(), postfix1.getExpression());
    }
}
