package hr.algebra.utilities;

import java.lang.Exception;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class ValidationUtils {

    public static void requireNonEmpty(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Vrijednost obavezna");
        }
    }

}
