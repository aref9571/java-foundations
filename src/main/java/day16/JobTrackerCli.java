package day16;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;
import day10.ApplicationRepository;
import day13.ApplicationService;
import day14.FileApplicationStore;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class JobTrackerCli {
    public static void main(String[] args){
        Path file = Path.of("jobtracker-data.txt");
        ApplicationRepository repository = new ApplicationRepository();
        ApplicationService service = new ApplicationService(repository);
        FileApplicationStore fileStore = new FileApplicationStore(file);

        List<JobApplication> existing = fileStore.loadAll();
        existing.forEach(repository::add);

        Money expectedSalary = new Money(5000L , "EUR");
        JobApplication created = repository.findFirstByCompany("Microsoft")
                .orElseGet(() -> service.applyForJob(
                        "Microsoft", "Backend", LocalDate.now(), expectedSalary));
        if (created.status() == ApplicationStatus.APPLIED) {
            service.moveToInterviewing(created.id());
        }

        fileStore.saveAll(repository.findAll());

        List<JobApplication> all = service.getAllApplications();
        System.out.println("Total applications: " + all.size());
        System.out.println("Active: " + service.getActiveCount());
        System.out.println("Final: " + service.getFinalCount());
        System.out.println();

        System.out.println("Summary:");
        Map<ApplicationStatus, Long> statusSummary = service.getStatusSummary();
        statusSummary.forEach((status, count) ->
                System.out.println("  " + status + ": " + count)
        );
        System.out.println();

        System.out.println("Top companies:");
        List<String> topCompanies = service.getTopCompanies(3);
        topCompanies.forEach(company ->
                System.out.println("  " + company)
        );

        System.out.println();
        System.out.println("Interviewing applications: "
                + service.findByStatus(ApplicationStatus.INTERVIEWING).size());
    }
}
