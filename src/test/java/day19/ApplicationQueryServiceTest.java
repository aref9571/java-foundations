package day19;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationQueryServiceTest {

    private ApplicationRepository repository;
    private ApplicationQueryService queryService;

    @BeforeEach
    void setup() {
        repository = new ApplicationRepository();
        seedData(repository);
        queryService = new ApplicationQueryService(repository);
    }

    @Test
    void findAppliedInLastDays_returnsOnlyRecentApplications_sortedNewestFirst() {
        int days = 20;

        List<JobApplication> result = queryService.findAppliedInLastDays(days);

        assertEquals(3, result.size());
        assertEquals("Google", result.getFirst().company());
        assertEquals("Amazon", result.get(1).company());
        assertEquals("BMW", result.getLast().company());
    }

    @Test
    void findAppliedInLastDays_negativeDays_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> queryService.findAppliedInLastDays(-1));
    }

    @Test
    void searchByRoleKeyword_matchesCaseInsensitiveSubstring_andSortsByCompanyThenRole() {
        String keyword = "dAta ENGineer";

        List<JobApplication> result = queryService.searchByRoleKeyword(keyword);

        assertEquals(1, result.size());
        JobApplication app = result.getFirst();
        assertEquals("Spotify", app.company());
        assertEquals("Data Engineer", app.role());
    }

    @Test
    void searchByRoleKeyword_noMatches_returnsEmptyList() {
        List<JobApplication> result = queryService.searchByRoleKeyword("NonExistingRole");
        assertTrue(result.isEmpty());
    }

    @Test
    void searchByRoleKeyword_blankKeyword_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> queryService.searchByRoleKeyword("   "));
        assertThrows(IllegalArgumentException.class,
                () -> queryService.searchByRoleKeyword(null));
    }

    @Test
    void findStuckApplications_returnsOnlyActiveOlderThanCutoff_sortedOldestFirst() {
        int daysWithoutChange = 14;

        List<JobApplication> result = queryService.findStuckApplications(daysWithoutChange);

        assertEquals(2, result.size());
        assertEquals("Spotify", result.getFirst().company());
        assertEquals("BMW", result.getLast().company());
    }

    @Test
    void findStuckApplications_nonPositiveDays_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> queryService.findStuckApplications(0));
        assertThrows(IllegalArgumentException.class,
                () -> queryService.findStuckApplications(-5));
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