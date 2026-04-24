package day17;

import java.util.List;
import java.util.Objects;

public class StreamBasics {

    public static List<Integer> filterEven(List<Integer> numbers){
        Objects.requireNonNull(numbers , "numbers must not be null");
        return numbers.stream().filter(n -> n % 2 == 0).toList();
    }

    public static List<String> uppercaseNonBlank(List<String> input){
        Objects.requireNonNull(input , "input must not be null");
        return input.stream().filter(Objects::nonNull).filter(s -> !s.isBlank()).map(String::toUpperCase).toList();

    }

    public static int sum(List<Integer> numbers){
        Objects.requireNonNull(numbers , "Numbers must not be null");
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    public static List<Integer> sortedDistinct(List<Integer> numbers){
        Objects.requireNonNull(numbers , "numbers must not be null");
        return numbers.stream().distinct().sorted().toList();
    }
}
