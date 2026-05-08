package day22;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrencyPatterns {
    public static List<Integer> doubleNumbersConcurrently(List<Integer> input)
        throws InterruptedException{
        if (input == null){
            return List.of();
        }
        ExecutorService executors = Executors.newFixedThreadPool(4);
        try {
            List<Callable<Integer>> tasks = input.stream().map(n -> (Callable<Integer>) () -> n * 2).toList();
            List<Future<Integer>> futures = executors.invokeAll(tasks);

            List<Integer> result = new ArrayList<>(futures.size());
            for (Future<Integer> future : futures){
                try {
                    result.add(future.get());
                }
                catch (ExecutionException e){
                    throw new RuntimeException("Task failed" , e);
                }
            }
            return result;
        }
        finally {
            executors.shutdown();
        }
    }
}
