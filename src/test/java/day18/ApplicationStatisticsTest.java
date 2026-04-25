package day18;
import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class ApplicationStatisticsTest {

    @Test
    void countByStatus_returnsCorrectCounts() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.INTERVIEWING)
                .build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.REJECTED)
                .build();
        JobApplication app4 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.REJECTED)
                .build();

        List<JobApplication> applications = List.of(app1, app2, app3, app4);

        Map<ApplicationStatus, Long> result = ApplicationStatistics.countByStatus(applications);

        assertEquals(1L, result.get(ApplicationStatus.APPLIED));
        assertEquals(1L, result.get(ApplicationStatus.INTERVIEWING));
        assertEquals(2L, result.get(ApplicationStatus.REJECTED));
        assertEquals(3, result.size());
    }

    @Test
    void countByCompany_isCaseInsensitiveAndCountsCorrectly() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Spotify")
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Amazon")
                .withStatus(ApplicationStatus.INTERVIEWING)
                .build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("amazon")
                .withStatus(ApplicationStatus.REJECTED)
                .build();
        JobApplication app4 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .withStatus(ApplicationStatus.REJECTED)
                .build();

        List<JobApplication> applications = List.of(app1, app2, app3, app4);

        Map<String, Long> result = ApplicationStatistics.countByCompany(applications);

        assertEquals(1L, result.get("spotify"));
        assertEquals(2L, result.get("amazon"));
        assertEquals(1L, result.get("google"));
        assertEquals(3, result.size());
    }

    @Test
    void topCompanies_returnsTopNCompaniesInDescendingOrder() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("GOOGLE")
                .build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("google")
                .build();

        JobApplication app4 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Amazon")
                .build();
        JobApplication app5 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("amazon")
                .build();

        JobApplication app6 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Spotify")
                .build();

        List<JobApplication> applications = List.of(app1, app2, app3, app4, app5, app6);

        List<String> top2 = ApplicationStatistics.topCompanies(applications, 2);

        assertEquals(2, top2.size());
        assertEquals("google", top2.get(0));
        assertEquals("amazon", top2.get(1));
    }

    @Test
    void countActiveAndFinal_sumToTotalApplications() {
        JobApplication active1 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.APPLIED)
                .build();
        JobApplication active2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.INTERVIEWING)
                .build();

        JobApplication final1 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.OFFER)
                .build();
        JobApplication final2 = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.REJECTED)
                .build();

        List<JobApplication> applications = List.of(active1, active2, final1, final2);

        long activeCount = ApplicationStatistics.countByActive(applications);
        long finalCount = ApplicationStatistics.countFinal(applications);

        assertEquals(applications.size(), activeCount + finalCount);
    }



}
