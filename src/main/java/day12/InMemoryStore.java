package day12;

import java.util.List;

public interface InMemoryStore<T> {
    void add(T item);
    List<T> findAll();
    int size();
}
