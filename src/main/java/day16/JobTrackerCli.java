package day16;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day05.Money;
import day10.ApplicationRepository;
import day13.ApplicationService;
import day14.FileApplicationStore;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class JobTrackerCli {
    public static void main(String[] args){
        Path file = Path.of("jobtracker-data.txt");
        ApplicationRepository repository = new ApplicationRepository();
        ApplicationService service = new ApplicationService(repository);
        FileApplicationStore fileStore = new FileApplicationStore(file);

        List<JobApplication> existing = fileStore.loadAll();
        for (JobApplication app : existing){
            repository.add(app);
        }

        Money expectedSalary = new Money(5000L , "EUR");
        JobApplication created = service.applyForJob(
                "Google" ,
                "Backend",
                LocalDate.now(),
                expectedSalary);
        service.moveToInterviewing(created.id());

        fileStore.saveAll(repository.findAll());

        System.out.println("Total applications: " + repository.findAll().size());
        System.out.println("Interviewing applications: " + service.findByStatus(ApplicationStatus.INTERVIEWING).size());
    }
}
