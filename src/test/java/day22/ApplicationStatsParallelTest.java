package day22;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationStatsParallelTest {
    @Test
    void computesStatsForNonEmptyList() throws InterruptedException, ExecutionException {
        JobApplication appActive1 = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication appActive2 = JobApplicationBuilder.aDefaultApplication().withStatus(ApplicationStatus.INTERVIEWING).build();
        JobApplication appFinal = JobApplicationBuilder.aDefaultApplication().withStatus(ApplicationStatus.REJECTED).build();
        List<JobApplication> applications = List.of(appActive1,appActive2 ,appFinal);

        long expectedActive = applications.stream().filter(JobApplication::isActive).count();
        long expectedFinal = applications.stream().filter(app -> !app.isActive()).count();

        ApplicationStatsParallel.StatsResult stats = ApplicationStatsParallel.computeStatsParallel(applications);
        assertEquals(expectedActive, stats.activeCount());
        assertEquals(expectedFinal, stats.finalCount());
    }

    @Test
    void returnsZeroCountsForEmptyList() throws InterruptedException, ExecutionException {
        List<JobApplication> applications = new ArrayList<>();
        ApplicationStatsParallel.StatsResult stats = ApplicationStatsParallel.computeStatsParallel(applications);
        assertEquals(0 , stats.activeCount());
        assertEquals(0 , stats.finalCount());
    }

    @Test
    void returnsZeroCountsForNullInput() throws InterruptedException, ExecutionException {
        ApplicationStatsParallel.StatsResult stats = ApplicationStatsParallel.computeStatsParallel(null);
        assertEquals(0 , stats.activeCount());
        assertEquals(0 , stats.finalCount());
    }
}
