package day26;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;

import java.time.LocalDate;

public class AnalyticsCli {

    public static void main(String[] args) throws InterruptedException {
        ApplicationRepository repository = new ApplicationRepository();
        seedSampleData(repository);

        AnalyticsService analyticsService = new AnalyticsService(repository);
        AnalyticsRefresher refresher = new AnalyticsRefresher(analyticsService);

        refresher.start(4, 5);

        for (int i = 0; i < 3; i++) {
            Thread.sleep(5000);
            AnalyticsSnapshot snapshot = refresher.getCurrentSnapshot();
            System.out.println("=== Snapshot " + (i + 1) + " ===");
            System.out.println("Total: " + snapshot.totalApplications());
            System.out.println("Active: " + snapshot.activeApplications());
            System.out.println("Final: " + snapshot.finalApplications());
            System.out.println("By status: " + snapshot.byStatus());
            System.out.println("Top companies: " + snapshot.topCompanies());

            analyticsService.mostRecentActive().ifPresentOrElse(
                    app -> System.out.println("Most recent active: " + app.company() + " - " + app.role()
                            + " (" + app.appliedDate() + ")"),
                    () -> System.out.println("No active applications")
            );
        }

        refresher.shutdown();
    }

    private static void seedSampleData(ApplicationRepository repository) {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .withRole("Backend Engineer")
                .withStatus(ApplicationStatus.APPLIED)
                .withAppliedDate(LocalDate.now().minusDays(2))
                .build();

        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google")
                .withRole("Platform Engineer")
                .withStatus(ApplicationStatus.INTERVIEWING)
                .withAppliedDate(LocalDate.now().minusDays(1))
                .build();

        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Microsoft")
                .withRole("Cloud Engineer")
                .withStatus(ApplicationStatus.REJECTED)
                .withAppliedDate(LocalDate.now().minusDays(5))
                .build();

        JobApplication app4 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Amazon")
                .withRole("Java Developer")
                .withStatus(ApplicationStatus.OFFER)
                .withAppliedDate(LocalDate.now().minusDays(3))
                .build();

        repository.add(app1);
        repository.add(app2);
        repository.add(app3);
        repository.add(app4);
    }
}
