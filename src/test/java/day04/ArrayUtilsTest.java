package day04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilsTest {


    @Test
    void copy_returns_null_when_source_is_null() {
        int[] copy = ArrayUtils.copy(null);

        assertNull(copy);
    }

    @Test
    void copy_returns_new_array_with_same_elements() {
        int[] source = {1, 2, 3};

        int[] copy = ArrayUtils.copy(source);

        assertNotNull(copy);
        assertEquals(source.length, copy.length);
        assertArrayEquals(source, copy);
        assertNotSame(source, copy);
    }


    @Test
    void reverse_returns_null_when_source_is_null() {
        int[] reversed = ArrayUtils.reverse(null);

        assertNull(reversed);
    }

    @Test
    void reverse_reverses_three_element_array() {
        int[] source = {1, 2, 3};

        int[] reversed = ArrayUtils.reverse(source);

        assertArrayEquals(new int[]{3, 2, 1}, reversed);
    }

    @Test
    void reverse_handles_single_element_array() {
        int[] source = {42};

        int[] reversed = ArrayUtils.reverse(source);

        assertArrayEquals(new int[]{42}, reversed);
        assertNotSame(source, reversed);
    }



    @Test
    void contains_returns_false_for_null_array() {
        boolean result = ArrayUtils.contains(null, 10);

        assertFalse(result);
    }

    @Test
    void contains_returns_true_when_value_present() {
        int[] source = {1, 2, 3};

        boolean result = ArrayUtils.contains(source, 2);

        assertTrue(result);
    }

    @Test
    void contains_returns_false_when_value_absent() {
        int[] source = {1, 2, 3};

        boolean result = ArrayUtils.contains(source, 5);

        assertFalse(result);
    }


    @Test
    void indexOf_returns_minus_one_for_null_array() {
        int index = ArrayUtils.indexOf(null, 10);

        assertEquals(-1, index);
    }

    @Test
    void indexOf_returns_first_match_when_duplicates() {
        int[] source = {1, 2, 2, 3};

        int index = ArrayUtils.indexOf(source, 2);

        assertEquals(1, index);
    }

    @Test
    void indexOf_returns_minus_one_when_not_found() {
        int[] source = {1, 2, 3};

        int index = ArrayUtils.indexOf(source, 99);

        assertEquals(-1, index);
    }


    @Test
    void safeGet_returns_default_when_array_is_null() {
        int result = ArrayUtils.safeGet(null, 0, -1);

        assertEquals(-1, result);
    }

    @Test
    void safeGet_returns_default_when_index_negative() {
        int[] source = {10, 20, 30};

        int result = ArrayUtils.safeGet(source, -1, -1);

        assertEquals(-1, result);
    }

    @Test
    void safeGet_returns_default_when_index_too_large() {
        int[] source = {10, 20, 30};

        int result = ArrayUtils.safeGet(source, 3, -1);

        assertEquals(-1, result);
    }

    @Test
    void safeGet_returns_element_for_valid_index() {
        int[] source = {10, 20, 30};

        int result = ArrayUtils.safeGet(source, 1, -1);

        assertEquals(20, result);
    }
}
