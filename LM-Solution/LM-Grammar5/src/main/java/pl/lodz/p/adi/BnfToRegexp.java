package pl.lodz.p.adi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BnfToRegexp {

    private static Pattern DEFINITIONS_PAT = Pattern.compile("(<?[a-z0-9']+>?)\\s*::=\\s*(.*)", Pattern.CASE_INSENSITIVE);
    private static Pattern TERMINAL_PAT = Pattern.compile("([^<>{}\\[\\]|a-z0-9'\" ])", Pattern.CASE_INSENSITIVE);
    private static Pattern REPETITION_PAT1 = Pattern.compile("\\{(.+?)\\}");
    private static Pattern REPETITION_PAT2 = Pattern.compile("\\[(.+?)\\]");
    private static Pattern REPETITION_PAT3 = Pattern.compile("(?:\\(\\?:)?(.*?)\\)?\\(\\?:\\1\\)\\*");

    public static Pattern compile(String[] bnf) {
        String[] symbol = new String[bnf.length];
        String[] definition = new String[bnf.length];

        // zamiana na parę symbol - definicja
        for (int i = 0; i < bnf.length; i++) {
            Matcher matcher = DEFINITIONS_PAT.matcher(bnf[i]);
            boolean find = matcher.find();
            symbol[i] = matcher.group(1);
            definition[i] = matcher.group(2);
        }

        // zmiana symbolu z E na <E>
        for (int i = bnf.length - 1; i >= 0; i--) {
            if (!symbol[i].startsWith("<")) {

                String sym = Pattern.quote(symbol[i]);
                symbol[i] = "<" + symbol[i] + ">";

                Pattern p = Pattern.compile("(^|[^<])(" + sym + ")");
                for (int j = bnf.length - 1; j >= 0; j--) {
                    definition[j] = p.matcher(definition[j]).replaceAll("$1<$2>");
                }
            }
        }

        // ulepszamy definicje, tak by stala sie regexpem
        for (int i = 0; i < bnf.length; i++) {
            String newDef = definition[i];
            newDef = TERMINAL_PAT.matcher(newDef).replaceAll("\\\\$1"); // escape terminali
            newDef = REPETITION_PAT1.matcher(newDef).replaceAll("(?:$1)*"); // zamiana powtórzeń na regexp
            newDef = REPETITION_PAT2.matcher(newDef).replaceAll("(?:$1)?"); // zamiana powtórzeń na regexp
            newDef = REPETITION_PAT3.matcher(newDef).replaceAll("(?:$1)+"); // zamiana kombinacji aa* na a+
            newDef = newDef.replace("0|1|2|3|4|5|6|7|8|9", "\\d"); // uproszczenie zapisu wszystkie cyfry
            newDef = newDef.replace("1|2|3|4|5|6|7|8|9", "[1-9]"); // uproszczenie zapisu cyfry bez zera

            newDef = newDef.replace("\\\\0", "\\d{0}"); // symbol pusty, czyli zero liczb

            if (newDef.contains("|")) {
                newDef = "(?:" + newDef + ")";
            }

            definition[i] = newDef;
        }

        // podstawiamy referencję do definicji we wszystkich odwołaniach
        for (int i = 0; i < definition.length; i++) {
            Pattern symbolPat = Pattern.compile(Pattern.quote(symbol[i]));
            String defRep = Matcher.quoteReplacement(definition[i]);

            // podstawiamy do kazdej wczesniejszej definicji
            for (int j = 0; j < definition.length; j++) {
//                if (i == j) continue;
                Matcher symbolMatcher = symbolPat.matcher(definition[j]);
                String newDef = symbolMatcher.replaceAll(defRep);
                definition[j] = newDef;
            }
        }
        for (int i = 0; i < bnf.length; i++) {
            System.out.printf("%s -> %s%n", symbol[i], definition[i]);
        }

        return Pattern.compile(definition[0]);
    }
}
