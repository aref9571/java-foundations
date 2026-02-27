package day02;
import java.util.Locale;

public final class StringToolkit {
    private StringToolkit(){}

    public static boolean isBlank(String a){
        return a == null || a.trim().isEmpty();
    }

    public static String requireNoneBlank(String s , String fieldName) {
        if (isBlank(fieldName)) {
            throw new IllegalArgumentException("fieldName must not be blank");
        }
        if (isBlank(s)){
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return s;
    }

    public static String normalizeEmail(String email){
        String raw = requireNoneBlank(email , "email");
        String normalized = raw.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()){
            throw new IllegalArgumentException("email must not be blank");
        }
        return normalized;
    }
}
