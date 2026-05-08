package day22;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

class ConcurrencyPatternsTest {
    @Test
    void doubleNumbersConcurrently_expectOutput() throws InterruptedException {
        List<Integer> input = List.of(1, 2, 3);
        List<Integer> result = ConcurrencyPatterns.doubleNumbersConcurrently(input);
        assertEquals(List.of(2, 4, 6), result);

    }

    @Test
    void doubleNumbersConcurrently_nullInput_returnEmptyList() throws InterruptedException {
        List<Integer> result = ConcurrencyPatterns.doubleNumbersConcurrently(null);
        assertTrue(result.isEmpty());
    }
    @Test
    void doubleNumbersConcurrently_emptyList_returnEmptyList() throws InterruptedException {
        List<Integer> input = List.of();
        List<Integer> result = ConcurrencyPatterns.doubleNumbersConcurrently(input);
        assertTrue(result.isEmpty());
    }
}
