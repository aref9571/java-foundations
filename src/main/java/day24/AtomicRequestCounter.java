package day24;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicRequestCounter implements RequestCounter{
    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void increment() {
        count.incrementAndGet();
    }

    @Override
    public int get() {
        return count.get();
    }

    @Override
    public void reset() {
        count.set(0);
    }

    @Override
    public int getAndReset() {
        return count.getAndSet(0);
    }
}
