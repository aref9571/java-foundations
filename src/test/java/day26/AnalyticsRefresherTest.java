package day26;

import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class AnalyticsRefresherTest {

    @Test
    void currentSnapshot_isEmptyBeforeStart() {
        AnalyticsRefresher refresher = new AnalyticsRefresher(new AnalyticsService(new ApplicationRepository()));

        AnalyticsSnapshot snapshot = refresher.getCurrentSnapshot();

        assertEquals(new AnalyticsSnapshot(0, 0, 0, Map.of(), List.of()), snapshot);
        refresher.shutdown();
    }

    @Test
    void start_replacesSnapshotWithRepositoryStatistics() throws InterruptedException {
        ApplicationRepository repository = new ApplicationRepository();
        repository.add(JobApplicationBuilder.aDefaultApplication().withCompany("Google").build());
        repository.add(JobApplicationBuilder.aDefaultApplication().withCompany("Amazon")
                .withStatus(ApplicationStatus.REJECTED).build());
        AnalyticsRefresher refresher = new AnalyticsRefresher(new AnalyticsService(repository));

        try {
            refresher.start(0, 1);
            AnalyticsSnapshot snapshot = waitForTotal(refresher, 2);

            assertEquals(2, snapshot.totalApplications());
            assertEquals(1, snapshot.activeApplications());
            assertEquals(1, snapshot.finalApplications());
        } finally {
            refresher.shutdown();
        }
    }

    @Test
    void constructor_rejectsNullService() {
        assertThrows(NullPointerException.class, () -> new AnalyticsRefresher(null));
    }

    private AnalyticsSnapshot waitForTotal(AnalyticsRefresher refresher, long expectedTotal) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 3000;
        AnalyticsSnapshot snapshot = refresher.getCurrentSnapshot();
        while (snapshot.totalApplications() != expectedTotal && System.currentTimeMillis() < deadline) {
            Thread.sleep(20);
            snapshot = refresher.getCurrentSnapshot();
        }
        return snapshot;
    }
}
