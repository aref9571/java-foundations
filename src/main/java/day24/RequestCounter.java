package day24;

public interface RequestCounter {
    void increment();
    int get();
    void reset();
    int getAndReset();
}
