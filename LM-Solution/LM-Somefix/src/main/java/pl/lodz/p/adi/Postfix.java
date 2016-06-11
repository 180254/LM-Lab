package pl.lodz.p.adi;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.function.BiFunction;

public class Postfix extends Somefix {

    private final Map<String, BiFunction<Double, Double, Double>> operators =
            new ImmutableMap.Builder<String, BiFunction<Double, Double, Double>>()
                    .put("+", (a, b) -> a + b)
                    .put("-", (a, b) -> a - b)
                    .put("*", (a, b) -> a * b)
                    .put("/", (a, b) -> a / b)
                    .put("%", (a, b) -> a % b)
                    .put("^", Math::pow)
                    .build();

    public Postfix(String expression) {
        super(expression);
    }

    public double calc() {
        Deque<Double> stack = new ArrayDeque<>();

        for (Object symbol : symbols) {
            if (symbol instanceof Double) {
                stack.push((Double) symbol);
            } else {
                Double a = stack.pop();
                Double b = stack.pop();
                Double result = operators.get((String) symbol).apply(b, a);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    public Infix toInfix() {
        Deque<String> stack = new ArrayDeque<>();

        for (Object symbol : symbols) {
            if (symbol instanceof Double) {
                stack.push(numberFormat.format(symbol));
                continue;
            }
            if (!"()+-*/%^".contains(((String) symbol))) {
                stack.push((String) symbol);
                continue;
            }

            String operator = (String) symbol;
            String a = stack.pop();
            String b = stack.pop();
            String format = String.format("(%s %s %s)", b, operator, a);
            stack.push(format);
        }

        return new Infix(stack.pop());
    }
}
