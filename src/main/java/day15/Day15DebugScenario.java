package day15;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day10.ApplicationRepository;
import day13.ApplicationService;
import day14.FileApplicationStore;


import java.nio.file.Path;
import java.util.*;

public class Day15DebugScenario {
    public static void main(String [] args){
        Path file = Path.of("Debug.csv");
        ApplicationRepository repository = new ApplicationRepository();
        ApplicationService service = new ApplicationService(repository);
        FileApplicationStore fileStore = new FileApplicationStore(file);


        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").build();
        repository.add(app1);
        repository.add(app2);
        fileStore.saveAll(repository.findAll());

        List<JobApplication> applications = fileStore.loadAll();
        fileStore.saveAll(applications);

        List<JobApplication> loaded = fileStore.loadAll();

        JobApplication firstLoaded = loaded.getFirst();
        service.moveToInterviewing(app1.id());
        repository.updateStatus(app1.id() , ApplicationStatus.OFFER);


    }
}
