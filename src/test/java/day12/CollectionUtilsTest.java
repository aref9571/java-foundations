package day12;

import static org.junit.jupiter.api.Assertions.*;

import day05.JobApplication;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class CollectionUtilsTest {

    @Test
    void firstOrNullReturnsFirstElement() {
        List<String> items = new ArrayList<>();
        items.add("Aref");
        items.add("Nasrin");
        String name = CollectionUtils.firstOrNull(items);

        assertEquals("Aref", name);
        assertNotNull(name);
    }

    @Test
    void firstOrNullReturnsNullForEmptyOrNullList() {
        List<JobApplication> applications = new ArrayList<>();
        JobApplication result = CollectionUtils.firstOrNull(applications);
        JobApplication resultForNull = CollectionUtils.firstOrNull(null);
        assertNull(resultForNull);
        assertNull(result);
    }

    @Test
    void containsNullDetectsNullValues() {
        List<String> items = new ArrayList<>();
        items.add("Aref");
        items.add(null);
        items.add("Nasrin");

        boolean result = CollectionUtils.containsNull(items);
        assertTrue(result);

    }

    @Test
    void copyReturnsUnmodifiableListWithSameElements() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);

        List<Integer> result = CollectionUtils.copy(numbers);

        assertEquals(numbers.get(0), result.get(0));
        assertEquals(numbers.get(1), result.get(1));
        assertEquals(numbers.size(), result.size());
        assertThrows(UnsupportedOperationException.class, () -> result.add(3));
    }

    @Test
    void copyReturnsEmptyUnmodifiableListForNullInput() {
        List<String> result = CollectionUtils.copy(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> result.add("Hello"));
    }

    @Test
    void totalSizeSumsLengthsOfStrings() {
        int result = CollectionUtils.totalSize(List.of("ab", "cde"));
        assertEquals(5, result);
    }

    @Test
    void totalSizeReturnsZeroForNullOrEmptyList() {
        assertEquals(0, CollectionUtils.totalSize(null));
        assertEquals(0, CollectionUtils.totalSize(List.of()));
    }

    @Test
    void totalSizeIgnoresNullElements() {
        List<String> items = new ArrayList<>();
        items.add("ab");
        items.add(null);
        items.add("c");

        int result = CollectionUtils.totalSize(items);

        assertEquals(3, result);
    }
}