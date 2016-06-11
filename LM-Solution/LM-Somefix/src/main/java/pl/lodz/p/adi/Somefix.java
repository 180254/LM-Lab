package pl.lodz.p.adi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public abstract class Somefix {

    protected final Locale locale = Locale.ROOT;
    protected final NumberFormat numberFormat = NumberFormat.getInstance(locale);

    protected final String expression;
    protected final List<Object> symbols;

    public Somefix(String expression) {
        this.expression = enhanceExpression(expression);
        this.symbols = parseSymbols(this.expression, locale);
    }

    public String getExpression() {
        return expression;
    }

    protected static String enhanceExpression(String expression) {
        return expression
                .replaceAll("([0-9]+(?:\\.[0-9]+)?)", " $1 ") // 13.55
                .replaceAll("([()\\[\\]{}+\\-*/%^])", " $1 ") //()[]{}+-*/%^
                .replaceAll("([a-z])", " $1 ") // a-z
                .replaceAll("\\s{2,}", " ")
                .trim();
    }

    protected static List<Object> parseSymbols(String expression, Locale locale) {
        List<Object> symbols = new ArrayList<>();
        Scanner scanner = new Scanner(expression);
        scanner.useLocale(locale);

        while (scanner.hasNext()) {
            Object symbol = scanner.hasNextDouble()
                    ? scanner.nextDouble()
                    : scanner.next();
            symbols.add(symbol);
        }

        return symbols;
    }
}
