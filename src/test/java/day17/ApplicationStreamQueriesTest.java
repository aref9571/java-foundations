package day17;
import static org.junit.jupiter.api.Assertions.*;

import day05.JobApplication;
import day05.JobApplicationBuilder;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class ApplicationStreamQueriesTest {
    @Test
    void filterByCompany_withMatchingCompany_returnsCorrectApplications(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withRole("Data Scientist").build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").withRole("Software Engineer").build();

        List<JobApplication> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);
        applications.add(app3);

        List<JobApplication> result = ApplicationStreamQueries.filterByCompany(applications,"amazon");

        assertEquals(2 , result.size());
        assertTrue(result.contains(app1));
        assertTrue(result.contains(app3));
        assertFalse(result.contains(app2));
        assertTrue(result.stream().allMatch(app -> app.company().equalsIgnoreCase("Amazon")));
    }
    @Test
    void filterByCompany_withNoMatchingCompany_returnsEmptyList() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Google").build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withCompany("Microsoft").build();

        List<JobApplication> applications = List.of(app1, app2);

        List<JobApplication> result = ApplicationStreamQueries.filterByCompany(applications, "Amazon");

        assertTrue(result.isEmpty());
    }
    @Test
    void filterByCompany_withBlankCompany_throwsException() {
        List<JobApplication> applications = List.of(
                JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").build()
        );

        assertThrows(IllegalArgumentException.class,
                () -> ApplicationStreamQueries.filterByCompany(applications, "   ")
        );
    }

    @Test
    void filterByCompany_withNullCompany_throwsException() {
        List<JobApplication> applications = List.of(
                JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").build()
        );

        assertThrows(IllegalArgumentException.class,
                () -> ApplicationStreamQueries.filterByCompany(applications, null)
        );
    }

    @Test
    void filterByCompany_withNullApplications_throwsException() {
        assertThrows(NullPointerException.class,
                () -> ApplicationStreamQueries.filterByCompany(null, "Amazon")
        );
    }

    @Test
    void filterByStatus_withMatchingStatus_returnsCorrectApplications() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.APPLIED)
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.INTERVIEWING)
                .build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.APPLIED)
                .build();

        List<JobApplication> applications = List.of(app1, app2, app3);

        List<JobApplication> result = ApplicationStreamQueries.filterByStatus(
                applications,
                day05.ApplicationStatus.APPLIED
        );

        assertEquals(2, result.size());
        assertTrue(result.contains(app1));
        assertTrue(result.contains(app3));
        assertFalse(result.contains(app2));
        assertTrue(result.stream().allMatch(app -> app.status() == day05.ApplicationStatus.APPLIED));
    }

    @Test
    void filterByStatus_withNoMatchingStatus_returnsEmptyList() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.INTERVIEWING)
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.REJECTED)
                .build();

        List<JobApplication> applications = List.of(app1, app2);

        List<JobApplication> result = ApplicationStreamQueries.filterByStatus(
                applications,
                day05.ApplicationStatus.APPLIED
        );

        assertTrue(result.isEmpty());
    }

    @Test
    void filterByStatus_withNullStatus_throwsException() {
        List<JobApplication> applications = List.of(
                JobApplicationBuilder.aDefaultApplication()
                        .withStatus(day05.ApplicationStatus.APPLIED)
                        .build()
        );

        assertThrows(IllegalArgumentException.class,
                () -> ApplicationStreamQueries.filterByStatus(applications, null)
        );
    }
    @Test
    void filterByStatus_withNullApplications_throwsException() {
        assertThrows(NullPointerException.class,
                () -> ApplicationStreamQueries.filterByStatus(null, day05.ApplicationStatus.APPLIED)
        );
    }
    @Test
    void listCompaniesSortedUnique_withEmptyList_returnsEmptyList() {
        List<JobApplication> applications = List.of();

        List<String> result = ApplicationStreamQueries.listCompaniesSortedUnique(applications);

        assertTrue(result.isEmpty());
    }

    @Test
    void listCompaniesSortedUnique_withDuplicates_returnsSortedUniqueUppercaseCompanies() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("google")
                .build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Amazon")
                .build();

        List<JobApplication> applications = List.of(app1, app2, app3);

        List<String> result = ApplicationStreamQueries.listCompaniesSortedUnique(applications);

        // Expect: ["AMAZON", "GOOGLE"] (sorted, unique, uppercase)
        assertEquals(List.of("AMAZON", "GOOGLE"), result);
    }

    @Test
    void countByStatus_withSomeMatches_returnsCorrectCount() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.APPLIED)
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.INTERVIEWING)
                .build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.APPLIED)
                .build();

        List<JobApplication> applications = List.of(app1, app2, app3);

        long count = ApplicationStreamQueries.countByStatus(
                applications,
                day05.ApplicationStatus.APPLIED
        );

        assertEquals(2L, count);
    }

    @Test
    void countByStatus_withNoMatches_returnsZero() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.INTERVIEWING)
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(day05.ApplicationStatus.REJECTED)
                .build();

        List<JobApplication> applications = List.of(app1, app2);

        long count = ApplicationStreamQueries.countByStatus(
                applications,
                day05.ApplicationStatus.APPLIED
        );

        assertEquals(0L, count);
    }

    @Test
    void countByStatus_withNullStatus_throwsException() {
        List<JobApplication> applications = List.of(
                JobApplicationBuilder.aDefaultApplication()
                        .withStatus(day05.ApplicationStatus.APPLIED)
                        .build()
        );

        assertThrows(IllegalArgumentException.class,
                () -> ApplicationStreamQueries.countByStatus(applications, null)
        );
    }


}
