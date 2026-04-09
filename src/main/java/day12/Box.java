package day12;

public final class Box<T> {
    private final T value;

    public Box(T value){
        this.value = value;
    }
    public T getValue(){
        return value;
    }
    public boolean isEmpty(){
        return value == null;
    }
}
