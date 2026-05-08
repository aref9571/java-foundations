package day22;

import day05.JobApplication;

import java.util.List;
import java.util.concurrent.*;

public class ApplicationStatsParallel {
    public record StatsResult(
            long activeCount,
            long finalCount
    ){}

    public static StatsResult computeStatsParallel(List<JobApplication> applications)
        throws InterruptedException, ExecutionException {

        if (applications == null){
            applications = List.of();
        }
        List<JobApplication> snapshot = List.copyOf(applications);
        ExecutorService executors = Executors.newFixedThreadPool(2);

        try {
            Callable<Long> activeTask = () -> snapshot.stream().filter(JobApplication::isActive).count();
            Callable<Long> finalTask = () -> snapshot.stream().filter(app -> !app.isActive()).count();

            Future<Long> activeFuture = executors.submit(activeTask);
            Future<Long> finalFuture = executors.submit(finalTask);

            long active = activeFuture.get();
            long fin = finalFuture.get();
            return new StatsResult(active,fin);
        }
        finally {
            executors.shutdown();
        }

    }
}
