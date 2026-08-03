package day21;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Main thread is: " + Thread.currentThread().getName());
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = runParallelSum(numbers);
        System.out.println("Sum = " + sum);
        System.out.println("Main thread done.");
    }





    public static int runParallelSum(List<Integer> numbers)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Callable<Integer> sumTask = () -> {
            System.out.println("Sum task running on: " + Thread.currentThread().getName());
            Thread.sleep(1000);
            int result = numbers.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Sum task finished on: " + Thread.currentThread().getName());
            return result;
        };

        try {
            Future<Integer> future = executor.submit(sumTask);
            System.out.println("Main submitted task, now waiting on future...");
            int result = future.get();
            System.out.println("Future completed, result received.");
            return result;
        } finally {
            executor.shutdown();
        }
    }
}
