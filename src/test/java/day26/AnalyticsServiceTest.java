package day26;

import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

public class AnalyticsServiceTest {
    private  ApplicationRepository repository;
    private AnalyticsService service;

    @BeforeEach
    void setUp(){
        repository = new ApplicationRepository();
        service = new AnalyticsService(repository);
        seedData(repository);
    }


    @Test
    void computeSnapshotShouldReturnCorrectAggregates(){
        AnalyticsSnapshot snapshot = service.computeSnapShot();

        assertEquals(4 , snapshot.totalApplications());
        assertEquals(3 , snapshot.activeApplications());
        assertEquals(1 , snapshot.finalApplications());

        assertEquals(2 , snapshot.byStatus().get(ApplicationStatus.APPLIED));
    }

    @Test
    void computeSnapshot_ordersTiedCompaniesAlphabetically(){
        AnalyticsSnapshot snapshot = service.computeSnapShot();

        assertEquals(List.of("amazon", "bmw", "google"), snapshot.topCompanies());
    }
    private void seedData(ApplicationRepository repository) {
        LocalDate today = LocalDate.now();

        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .withRole("Backend Engineer")
                .withAppliedDate(today.minusDays(20))
                .withStatus(ApplicationStatus.APPLIED)
                .build();

        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("BMW")
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
