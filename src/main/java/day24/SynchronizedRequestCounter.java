package day24;

public class SynchronizedRequestCounter implements RequestCounter{
    private int count = 0;
    @Override
    public synchronized void increment() {
        count++;
    }

    @Override
    public synchronized int get() {
        return count;
    }

    @Override
    public synchronized void reset() {
        count = 0;
    }

    @Override
    public synchronized int getAndReset() {
        int current = count;
        count = 0;
        return current;
    }
}
