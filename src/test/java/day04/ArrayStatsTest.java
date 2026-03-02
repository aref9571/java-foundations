package day04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStatsTest {


    @Test
    void min_returns_smallest_for_ascending_array() {
        int[] values = {1, 2, 3};

        int result = ArrayStats.min(values);

        assertEquals(1, result);
    }

    @Test
    void min_handles_mixed_negative_and_positive_values() {
        int[] values = {-5, 0, 100};

        int result = ArrayStats.min(values);

        assertEquals(-5, result);
    }

    @Test
    void min_handles_single_element_array() {
        int[] values = {42};

        int result = ArrayStats.min(values);

        assertEquals(42, result);
    }

    @Test
    void min_throws_exception_for_null_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.min(null));
    }

    @Test
    void min_throws_exception_for_empty_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.min(new int[0]));
    }


    @Test
    void max_returns_largest_for_ascending_array() {
        int[] values = {1, 2, 3};

        int result = ArrayStats.max(values);

        assertEquals(3, result);
    }

    @Test
    void max_handles_mixed_negative_and_positive_values() {
        int[] values = {-5, 0, 100};

        int result = ArrayStats.max(values);

        assertEquals(100, result);
    }

    @Test
    void max_handles_single_element_array() {
        int[] values = {42};

        int result = ArrayStats.max(values);

        assertEquals(42, result);
    }

    @Test
    void max_throws_exception_for_null_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.max(null));
    }

    @Test
    void max_throws_exception_for_empty_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.max(new int[0]));
    }


    @Test
    void sum_returns_total_for_multiple_elements() {
        int[] values = {1, 2, 3};

        int result = ArrayStats.sum(values);

        assertEquals(6, result);
    }

    @Test
    void sum_handles_single_element_array() {
        int[] values = {5};

        int result = ArrayStats.sum(values);

        assertEquals(5, result);
    }

    @Test
    void sum_throws_exception_for_null_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.sum(null));
    }

    @Test
    void sum_throws_exception_for_empty_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.sum(new int[0]));
    }


    @Test
    void average_computes_mean_for_evenly_spaced_values() {
        int[] values = {2, 4, 6};

        double result = ArrayStats.average(values);

        assertEquals(4.0, result);
    }

    @Test
    void average_returns_same_value_for_single_element() {
        int[] values = {1};

        double result = ArrayStats.average(values);

        assertEquals(1.0, result);
    }

    @Test
    void average_supports_fractional_result() {
        int[] values = {1, 2};

        double result = ArrayStats.average(values);

        assertEquals(1.5, result);
    }

    @Test
    void average_throws_exception_for_null_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.average(null));
    }

    @Test
    void average_throws_exception_for_empty_array() {
        assertThrows(IllegalArgumentException.class,
                () -> ArrayStats.average(new int[0]));
    }
}
