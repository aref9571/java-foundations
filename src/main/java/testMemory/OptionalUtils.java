package testMemory;

import day05.ApplicationStatus;
import day05.JobApplication;
import day10.ApplicationRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public  class OptionalUtils{
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
