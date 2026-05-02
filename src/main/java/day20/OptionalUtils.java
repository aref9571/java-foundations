package day20;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class OptionalUtils {

    public static <T> Optional<T> safeOf(T value){
        return Optional.ofNullable(value);
    }

    public static String orDefault(Optional<String> opt , String defaultValue){
        return opt.orElse(defaultValue);
    }

    public static int lengthOrZero(Optional<String> opt){
        return opt.map(String::length).orElse(0);
    }

    public static <T> Optional<T> firstMatch(List<T> items , Predicate<T> predicate){
        if (items == null || predicate == null){
            return Optional.empty();
        }
        return items.stream().filter(predicate).findFirst();
    }
}
