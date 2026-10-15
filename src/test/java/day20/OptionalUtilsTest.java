package day20;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class OptionalUtilsTest {

    @Test
    void safeOf_returnsEmptyForNull() {
        Optional<String> result = OptionalUtils.safeOf(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void safeOf_returnsValueWhenPresent() {
        Optional<String> result = OptionalUtils.safeOf("Google");

        assertEquals(Optional.of("Google"), result);
    }

    @Test
    void orDefault_returnsValueWhenPresent() {
        String result = OptionalUtils.orDefault(Optional.of("Google"), "unknown");

        assertEquals("Google", result);
    }

    @Test
    void orDefault_returnsDefaultWhenEmpty() {
        String result = OptionalUtils.orDefault(Optional.empty(), "unknown");

        assertEquals("unknown", result);
    }

    @Test
    void lengthOrZero_returnsLengthWhenPresent() {
        int result = OptionalUtils.lengthOrZero(Optional.of("Amazon"));

        assertEquals(6, result);
    }

    @Test
    void lengthOrZero_returnsZeroWhenEmpty() {
        int result = OptionalUtils.lengthOrZero(Optional.empty());

        assertEquals(0, result);
    }

    @Test
    void firstMatch_returnsFirstMatchingItem() {
        List<Integer> numbers = List.of(1, 4, 6);

        Optional<Integer> result = OptionalUtils.firstMatch(numbers, n -> n % 2 == 0);

        assertEquals(Optional.of(4), result);
    }

    @Test
    void firstMatch_returnsEmptyWhenNothingMatches() {
        List<Integer> numbers = List.of(1, 3, 5);

        Optional<Integer> result = OptionalUtils.firstMatch(numbers, n -> n % 2 == 0);

        assertTrue(result.isEmpty());
    }

    @Test
    void firstMatch_returnsEmptyForNullListOrPredicate() {
        assertTrue(OptionalUtils.firstMatch(null, n -> true).isEmpty());
        assertTrue(OptionalUtils.firstMatch(List.of(1), null).isEmpty());
    }
}
