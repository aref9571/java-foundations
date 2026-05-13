package day24;

import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

class StatsRefresherTest {
    private ApplicationRepository repository;

    @BeforeEach
    void setUp(){
        repository = new ApplicationRepository();
        seedData(repository);
    }

    @Test
    void refresherUpdatesSnapshotOverTime() throws InterruptedException {
        StatsRefresher refresher = new StatsRefresher(repository);
        refresher.start(0,1);
        Thread.sleep(1500);
        StatsRefresher.StatsSnapshot snapshot = refresher.getCurrentSnapshot();
        assertEquals(3 , snapshot.activeCount());
        assertEquals(1 , snapshot.finalCount());
        refresher.shutdown();
    }

    @Test
    void forceRefreshUpdatesSnapshotImmediately(){


        StatsRefresher refresher = new StatsRefresher(repository);
        refresher.forceRefresh();
        StatsRefresher.StatsSnapshot snapshot = refresher.getCurrentSnapshot();
        assertEquals(3 , snapshot.activeCount());
        assertEquals(1 , snapshot.finalCount());
        refresher.shutdown();

    }






    private void seedData(ApplicationRepository repository) {
        LocalDate today = LocalDate.now();

        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("BMW")
                .withRole("Backend Engineer")
                .withAppliedDate(today.minusDays(20))
                .withStatus(ApplicationStatus.APPLIED)
                .build();

        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .withRole("Backend Engineer")
                .withAppliedDate(today.minusDays(2))
                .withStatus(ApplicationStatus.APPLIED)
                .build();

        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Spotify")
                .withRole("Data Engineer")
                .withAppliedDate(today.minusDays(30))
                .withStatus(ApplicationStatus.INTERVIEWING)
                .build();

        JobApplication app4 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Amazon")
                .withRole("Backend Engineer")
                .withAppliedDate(today.minusDays(5))
                .withStatus(ApplicationStatus.REJECTED)
                .build();

        repository.add(app1);
        repository.add(app2);
        repository.add(app3);
        repository.add(app4);
    }
}
