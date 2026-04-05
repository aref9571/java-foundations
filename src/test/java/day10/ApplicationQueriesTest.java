package day10;
import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class ApplicationQueriesTest {

    @Test
    void filterByTitleKeyword_matchesSomeApplications_caseInsensitive() {
        Money salary = new Money(1000L, "EUR");
        LocalDate date = LocalDate.of(2026, 4, 5);
        JobApplication app1 = new JobApplication("Meta", "BACKEND", ApplicationStatus.APPLIED, date, salary, UUID.randomUUID());
        JobApplication app2 = new JobApplication("Amazon", "Engineer", ApplicationStatus.INTERVIEWING, date, salary, UUID.randomUUID());
        JobApplication app3 = new JobApplication("Amazon", "backend", ApplicationStatus.REJECTED, date, salary, UUID.randomUUID());
        List<JobApplication> list = new ArrayList<>();
        list.add(app1);
        list.add(app2);
        list.add(app3);

        List<JobApplication> result = ApplicationQueries.filterByTitleKeyword(list, "Backend");

        assertEquals(2, result.size());
        assertTrue(result.contains(app1));
        assertTrue(result.contains(app3));
        assertFalse(result.contains(app2));
    }

    @Test
    void filterByTitleKeyword_noMatches_returnsEmptyList() {
        Money salary = new Money(1000L, "EUR");
        LocalDate date = LocalDate.of(2026, 4, 5);
        JobApplication app = new JobApplication("Meta", "BACKEND", ApplicationStatus.APPLIED, date, salary, UUID.randomUUID());
        List<JobApplication> list = List.of(app);

        List<JobApplication> result = ApplicationQueries.filterByTitleKeyword(list, "Engineer");

        assertTrue(result.isEmpty());
    }

    @Test
    void filterByTitleKeyword_nullList_returnsEmptyList() {
        List<JobApplication> result = ApplicationQueries.filterByTitleKeyword(null, "Engineer");
        assertTrue(result.isEmpty());
    }

    @Test
    void filterByTitleKeyword_blankKeyword_throwsException() {
        List<JobApplication> applications = List.of();
        assertThrows(IllegalArgumentException.class,
                () -> ApplicationQueries.filterByTitleKeyword(applications, " "));
    }

    @Test
    void filterAppliedAfter_returnsOnlyApplicationsAfterGivenDate() {
        Money salary = new Money(1000L, "EUR");
        LocalDate date1 = LocalDate.of(2026, 4, 5);
        LocalDate date2 = LocalDate.of(2020, 4, 5);
        LocalDate date3 = LocalDate.of(2026, 1, 1);

        JobApplication app1 = new JobApplication("Meta", "BACKEND", ApplicationStatus.APPLIED, date1, salary, UUID.randomUUID());
        JobApplication app2 = new JobApplication("Amazon", "Engineer", ApplicationStatus.INTERVIEWING, date2, salary, UUID.randomUUID());
        JobApplication app3 = new JobApplication("Amazon", "backend", ApplicationStatus.REJECTED, date3, salary, UUID.randomUUID());
        List<JobApplication> list = List.of(app1, app2, app3);

        LocalDate appliedAfter = LocalDate.of(2026, 1, 1);
        List<JobApplication> result = ApplicationQueries.filterAppliedAfter(list, appliedAfter);

        assertEquals(1, result.size());
        assertTrue(result.contains(app1));
        assertFalse(result.contains(app2));
        assertFalse(result.contains(app3));
    }

    @Test
    void filterAppliedAfter_nullList_returnsEmptyList() {
        List<JobApplication> result = ApplicationQueries.filterAppliedAfter(null, LocalDate.of(2026, 1, 1));
        assertTrue(result.isEmpty());
    }

    @Test
    void filterAppliedAfter_nullDate_throwsException() {
        List<JobApplication> applications = List.of();
        assertThrows(IllegalArgumentException.class,
                () -> ApplicationQueries.filterAppliedAfter(applications, null));
    }

    @Test
    void filterByCompanyPrefix_matchesByPrefix_caseInsensitive() {
        Money salary = new Money(1000L, "EUR");
        LocalDate date1 = LocalDate.of(2026, 4, 5);
        LocalDate date2 = LocalDate.of(2020, 4, 5);
        LocalDate date3 = LocalDate.of(2026, 1, 1);

        JobApplication app1 = new JobApplication("Meta", "BACKEND", ApplicationStatus.APPLIED, date1, salary, UUID.randomUUID());
        JobApplication app2 = new JobApplication("AMAZON", "Engineer", ApplicationStatus.INTERVIEWING, date2, salary, UUID.randomUUID());
        JobApplication app3 = new JobApplication("amazon", "backend", ApplicationStatus.REJECTED, date3, salary, UUID.randomUUID());
        List<JobApplication> list = List.of(app1, app2, app3);

        List<JobApplication> result = ApplicationQueries.filterByCompanyPrefix(list, "amA");

        assertEquals(2, result.size());
        assertTrue(result.contains(app2));
        assertTrue(result.contains(app3));
        assertFalse(result.contains(app1));
    }

    @Test
    void filterByCompanyPrefix_noMatches_returnsEmptyList() {
        Money salary = new Money(1000L, "EUR");
        LocalDate date = LocalDate.of(2026, 4, 5);
        JobApplication app1 = new JobApplication("Meta", "BACKEND", ApplicationStatus.APPLIED, date, salary, UUID.randomUUID());
        JobApplication app2 = new JobApplication("Google", "Engineer", ApplicationStatus.INTERVIEWING, date, salary, UUID.randomUUID());
        List<JobApplication> list = List.of(app1, app2);

        List<JobApplication> result = ApplicationQueries.filterByCompanyPrefix(list, "zz");

        assertTrue(result.isEmpty());
    }

    @Test
    void filterByCompanyPrefix_nullList_returnsEmptyList() {
        List<JobApplication> result = ApplicationQueries.filterByCompanyPrefix(null, "go");
        assertTrue(result.isEmpty());
    }

    @Test
    void filterByCompanyPrefix_blankPrefix_throwsException() {
        List<JobApplication> applications = List.of();
        assertThrows(IllegalArgumentException.class,
                () -> ApplicationQueries.filterByCompanyPrefix(applications, " "));
    }
}