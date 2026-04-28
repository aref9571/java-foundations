package day19;
import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;
import day14.FileApplicationStore;

import java.nio.file.Path;


import java.time.LocalDate;
import java.util.List;


public class QueryCli {
    public static void main(String[] args){
        ApplicationRepository repository= new ApplicationRepository();
        QueryCli cli = new QueryCli();
        cli.seedData(repository);
        Path file = Path.of("applications.csv");
        FileApplicationStore store = new FileApplicationStore(file);
        List<JobApplication> loaded = store.loadAll();
        loaded.forEach(repository::add);
        ApplicationQueryService queryService = new ApplicationQueryService(repository);

        printRecentApplications(queryService);
        printBackendRoles(queryService);
        printStuckApplications(queryService);
    }


    private static void printRecentApplications(ApplicationQueryService queryService){
        System.out.println("Recent applications (last 7 days):");
        List<JobApplication> loaded = queryService.findAppliedInLastDays(7);
        if (loaded.isEmpty()){
            System.out.println("No application!");
            System.out.println();
        }
        else {
            loaded.forEach(app ->
                    System.out.println(" " + app.appliedDate()
                            + " " + app.company()
                            + " - " + app.role()));
            System.out.println();
        }
    }
    private static void printBackendRoles(ApplicationQueryService queryService){
        System.out.println("Backend Roles:");
        List<JobApplication> loaded = queryService.searchByRoleKeyword("backend");
        if (loaded.isEmpty()){
            System.out.println("No application!");
            System.out.println();
        }
        else {
            loaded.forEach(app ->
                    System.out.println(" " + app.company()
                            + " - " + app.role()
                            + " (" + app.status() + ")"));
            System.out.println();

        }
    }

    private static void printStuckApplications(ApplicationQueryService queryService) {
        System.out.println("Stuck applications (older than 30 days, still active):");

        List<JobApplication> loaded = queryService.findStuckApplications(30);
        if (loaded.isEmpty()){
            System.out.println("No application!");
        }
        else {
            loaded.forEach(app ->
                    System.out.println("  " + app.appliedDate()
                            + "  " + app.company()
                            + " - " + app.role()
                            + " (" + app.status() + ")"));
            System.out.println();
        }
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
