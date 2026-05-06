package day21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorExamplesRace {
    public static int safeIncrement(int iterations) throws InterruptedException{
        AtomicInteger counter = new AtomicInteger(0);

        Runnable task = () -> {
            for (int i = 0; i < iterations; i++) {
                counter.incrementAndGet();
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();

        boolean finished = executor.awaitTermination(1 , TimeUnit.MINUTES);
        if (!finished){
            System.out.println("Task did not finish in time!");
        }
        return counter.get();
    }

    public static int unsafeIncrement(int iterations) throws
            InterruptedException {
        final int[] counter = {0};

        Runnable task = () -> {
            for (int i = 0; i < iterations; i++) {
                counter[0]++; // still NOT thread-safe
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();

        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);

        if(!finished){
            System.out.println("Tasks did not finish in time!");
        }
        return counter[0];
    }
}
