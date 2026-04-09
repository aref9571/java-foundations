package day12;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListInMemoryTest {
    @Test
    void addStoresItemsAndSizeIncreases() {
        ListInMemoryStore<String> store = new ListInMemoryStore<>();

        store.add("Aref");
        store.add("Nasrin");

        assertEquals(2, store.size());
        List<String> all = store.findAll();
        assertEquals(List.of("Aref", "Nasrin"), all);
    }

    @Test
    void addNullThrowsIllegalArgumentException() {
        ListInMemoryStore<String> store = new ListInMemoryStore<>();

        assertThrows(IllegalArgumentException.class, () -> store.add(null));
    }

    @Test
    void findAllReturnsCopyWithAllItems() {
        ListInMemoryStore<Integer> store = new ListInMemoryStore<>();

        store.add(1);
        store.add(2);

        List<Integer> firstResult = store.findAll();
        List<Integer> secondResult = store.findAll();

        assertEquals(List.of(1, 2), firstResult);
        assertEquals(List.of(1, 2), secondResult);
        assertNotSame(firstResult, secondResult);
    }

    @Test
    void findAllReturnsUnmodifiableList() {
        ListInMemoryStore<String> store = new ListInMemoryStore<>();

        store.add("one");
        store.add("two");

        List<String> all = store.findAll();

        assertThrows(UnsupportedOperationException.class, () -> all.add("three"));
    }
}

