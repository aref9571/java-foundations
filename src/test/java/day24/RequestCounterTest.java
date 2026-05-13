package day24;

import org.junit.jupiter.api.Test;

import static day23.CounterPerformance.runCounterTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestCounterTest {
    @Test
    void atomicCounterCountsCorrectly() throws InterruptedException {
        RequestCounter counter = new AtomicRequestCounter();
        int threads = 8;
        int iterations = 100_000;
        runConcurrentIncrements(counter,threads,iterations);
        assertEquals(threads * iterations , counter.get());

    }

    private void runConcurrentIncrements(RequestCounter counter , int threadCount , int iterations)
        throws InterruptedException{
        Thread[] threads = new Thread[threadCount];

        Runnable task = () -> {
            for (int i = 0 ; i < iterations ; i++){
                counter.increment();
            }
        };

        for (int i = 0 ; i < threadCount ; i++){
            threads[i] = new Thread(task);
            threads[i].start();;
        }

        for (Thread t : threads){
            t.join();
        }

    }
    @Test
    void synchronizedCounterCountsCorrectly() throws InterruptedException {
        RequestCounter counter = new SynchronizedRequestCounter();
        int threads = 8;
        int iterations = 100_000;
        runConcurrentIncrements(counter , threads , iterations);
        assertEquals(threads * iterations , counter.get());


    }
    @Test
    void synchronizedCounterGetAndResetWorks() {
        RequestCounter counter = new SynchronizedRequestCounter();
        counter.increment();
        counter.increment();
        int value = counter.getAndReset();

        assertEquals(2, value);
        assertEquals(0, counter.get());
    }

    @Test
    void atomicCounterGetAndResetWorks() {
        RequestCounter counter = new AtomicRequestCounter();
        counter.increment();
        counter.increment();
        int value = counter.getAndReset();

        assertEquals(2 , value);
        assertEquals(0,counter.get());
    }


}
