package day12;

import java.util.ArrayList;
import java.util.List;

public class ListInMemoryStore<T> implements InMemoryStore<T>{
    private final List<T> items = new ArrayList<>();
    @Override
    public void add(T item) {
        if (item == null){
            throw new IllegalArgumentException("item must not be null");
        }
        items.add(item);

    }

    @Override
    public List<T> findAll() {
        return List.copyOf(items);
    }

    @Override
    public int size() {
        return items.size();
    }
}
