package pl.lodz.p.adi;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Infix extends Somefix {

    private final Map<String, Integer> priorities =
            new ImmutableMap.Builder<String, Integer>()
                    .put("(", 0)
                    .put("+", 1).put("-", 1).put(")", 1)
                    .put("*", 2).put("/", 2).put("%", 2)
                    .put("^", 3)
                    .build();
    private final Map<String, String> associativities =
            new ImmutableMap.Builder<String, String>()
                    .put("+", "left").put("-", "left")
                    .put("*", "left").put("/", "left")
                    .put("%", "left").put("^", "right")
                    .build();

    public Infix(String expression) {
        super(expression);
    }

    @Override
    public String getExpression() {
        return super.getExpression().replaceAll("\\s+", "");
    }

    public double calc() {
        return toPostfix().calc();
    }

    public Postfix toPostfix() {
        StringBuilder out = new StringBuilder();
        Deque<String> stack = new ArrayDeque<>();

        for (Object symbol : symbols) {
            if (symbol instanceof Double) {
                out.append(numberFormat.format(symbol)).append(" ");
                continue;
            }
            if (!"()+-*/%^".contains(((String) symbol))) {
                out.append(symbol).append(" ");
                continue;
            }

            String operator = (String) symbol;

            if (operator.equals("(")) {
                stack.push(operator);
                continue;
            }

            if (operator.equals(")")) {
                while (!stack.peek().equals("(")) {
                    out.append(stack.pop()).append(" ");
                }
                stack.pop(); // (
                continue;
            }

            while (!stack.isEmpty() &&
                    (associativities.get(operator).equals("left")
                            && priorities.get(stack.peek()) >= priorities.get(operator))
                    ||
                    (associativities.get(operator).equals("right")
                            && priorities.get(stack.peek()) > priorities.get(operator))
                    ) {
                out.append(stack.pop()).append(" ");
            }
            stack.push(operator);
        }

        while (!stack.isEmpty()) {
            out.append(stack.pop()).append(" ");
        }

        return new Postfix(out.toString().trim());
    }
}
