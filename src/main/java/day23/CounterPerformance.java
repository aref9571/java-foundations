package day23;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterPerformance {

    public static class AtomicCounter {
        private final AtomicInteger value = new AtomicInteger(0);

        public void increment() {
            value.incrementAndGet();
        }

        public int get() {
            return value.get();
        }
    }

    public static class SynchronizedCounter {
        private int value = 0;

        public synchronized void increment() {
            value++;
        }

        public synchronized int get() {
            return value;
        }
    }

    public static long runCounterTest(Runnable incrementTask,
                                      int threadCount,
                                      int iterationsPerThread) throws InterruptedException {

        Thread[] threads = new Thread[threadCount];
        long start = System.nanoTime();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(incrementTask);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.nanoTime();
        return end - start;
    }

    public static long measureAtomic(int threadCount,
                                     int iterationsPerThread) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        Runnable task = () -> {
            for (int i = 0; i < iterationsPerThread; i++) {
                counter.increment();
            }
        };
        long duration = runCounterTest(task, threadCount, iterationsPerThread);
        System.out.println("Atomic: value=" + counter.get() + ", time=" + duration);
        return duration;
    }

    public static long measureSynchronized(int threadCount,
                                           int iterationsPerThread) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Runnable task = () -> {
            for (int i = 0; i < iterationsPerThread; i++) {
                counter.increment();
            }
        };
        long duration = runCounterTest(task, threadCount, iterationsPerThread);
        System.out.println("Synchronized: value=" + counter.get() + ", time=" + duration);
        return duration;
    }

    public static void main(String[] args) throws InterruptedException {
        int iterations = 100_000;

        int threadsCorrectness = 8;
        measureAtomic(threadsCorrectness, iterations);
        measureSynchronized(threadsCorrectness, iterations);
        System.out.println("--------------------------------------------------------");

        int lowContentionThreads = 2;
        measureAtomic(lowContentionThreads, 1_000_000);
        measureSynchronized(lowContentionThreads, 1_000_000);
        System.out.println("--------------------------------------------------------");

        int moderateContentionThreads = 8;
        measureAtomic(moderateContentionThreads, 1_000_000);
        measureSynchronized(moderateContentionThreads, 1_000_000);
        System.out.println("--------------------------------------------------------");

        int highContentionThreads = 32;
        measureAtomic(highContentionThreads, 1_000_000);
        measureSynchronized(highContentionThreads, 1_000_000);
        System.out.println("--------------------------------------------------------");
    }

}