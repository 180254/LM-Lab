package pl.p.lodz.p.adi;

import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.adi.t.turing.TmResult;
import pl.lodz.p.adi.t.turing.TuringFactory;
import pl.lodz.p.adi.t.turing.TuringMachine;

import java.util.Arrays;

public class ParkingMachineTests {

    public static final int PAID = 17;
    public static final int NOT_PAID = 18;

    private final TuringMachine parkingMachine = TuringFactory.parkingMachine();

    private static String sort(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private static boolean equals(String str1, String str2) {
        return sort(str1).equals(sort(str2));
    }

    @Test
    public void test_false10() {
        String tape = "1";
        String expected = "1";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false20() {
        String tape = "2";
        String expected = "2";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false30() {
        String tape = "12";
        String expected = "12";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false40() {
        String tape = "121";
        String expected = "22";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false41() {
        String tape = "1111";
        String expected = "22";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false42() {
        String tape = "112";
        String expected = "22";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false43() {
        String tape = "22";
        String expected = "22";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }


    @Test
    public void test_false50() {
        String tape = "221";
        String expected = "5";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false51() {
        String tape = "5";
        String expected = "5";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false52() {
        String tape = "11111";
        String expected = "5";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false53() {
        String tape = "1112";
        String expected = "5";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false60() {
        String tape = "222";
        String expected = "51";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false61() {
        String tape = "51";
        String expected = "51";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false62() {
        String tape = "111111";
        String expected = "51";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_false63() {
        String tape = "2211";
        String expected = "51";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(NOT_PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }


    @Test
    public void test_true00() {
        String tape = "25";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true01() {
        String tape = "1111111";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true02() {
        String tape = "2221";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true03() {
        String tape = "21121";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true04() {
        String tape = "111121";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true05() {
        String tape = "2212";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true06() {
        String tape = "2122";
        String expected = "";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true10() {
        String tape = "251";
        String expected = "1";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true20() {
        String tape = "252";
        String expected = "2";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true30() {
        String tape = "2215";
        String expected = "12";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true40() {
        String tape = "2225";
        String expected = "22";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true51() {
        String tape = "255";
        String expected = "5";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }


    @Test
    public void test_true52() {
        String tape = "22255";
        String expected = "225";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }


    @Test
    public void test_true53() {
        String tape = "125222";
        String expected = "1222";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }

    @Test
    public void test_true54() {
        String tape = "5122211";
        String expected = "12211";
        TmResult execute = parkingMachine.execute(tape);
        Assert.assertEquals(PAID, execute.getState());
        Assert.assertTrue(execute.getTape(), equals(expected, execute.getTape()));
    }
}
