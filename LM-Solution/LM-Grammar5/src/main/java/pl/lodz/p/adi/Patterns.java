package pl.lodz.p.adi;

import com.google.common.util.concurrent.SimpleTimeLimiter;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Patterns {

    private static SimpleTimeLimiter stl = new SimpleTimeLimiter();

    public static boolean matches(Pattern pattern, String str) {
        try {
            return stl.callWithTimeout(
                    () -> pattern.matcher(str).matches(),
                    250, TimeUnit.MILLISECONDS, false);

        } catch (Exception | Error e) {
            System.out.println("Patterns.Catch " + e.getClass().getName());
            return false;
        }
    }
}
