package day13;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;
import day10.ApplicationRepository;
import day18.ApplicationStatistics;
import java.util.Map;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ApplicationService {
    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository){
        this.repository = Objects.requireNonNull(repository , "repository must not be null");
    }
    public List<JobApplication> getAllApplications(){
        return repository.findAll();
    }
    public JobApplication applyForJob(String company , String role , LocalDate appliedDate , Money expectedSalary){
        validateApplicationInput(company ,role ,appliedDate ,expectedSalary);
        UUID id = UUID.randomUUID();
        JobApplication application = new JobApplication(company,role, ApplicationStatus.APPLIED , appliedDate ,expectedSalary ,id);
        repository.add(application);
        System.out.println("[INFO] [ApplicationService] Created application | " +
                "id=" + id +
                ", company=" + company +
                ", role=" + role +
                ", status=" + application.status());
        return application;
    }
    public List<JobApplication> findByCompany(String company){
        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("company must be non blank");
        }
        return repository.findByCompany(company);
    }
    public List<JobApplication> findByStatus(ApplicationStatus status){
        if (status == null){
            throw new IllegalArgumentException("status must not be null");
        }
        return repository.findByStatus(status);
    }
    public JobApplication moveToInterviewing(UUID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
        System.out.println("[DEBUG] [ApplicationService] Moving to INTERVIEWING | id=" + id);
        JobApplication updated = repository.updateStatus(id , ApplicationStatus.INTERVIEWING);
        System.out.println("[INFO] [ApplicationService] Moved to INTERVIEWING | id=" + id +
                ", newStatus=" + updated.status());
        return updated;
    }
    public JobApplication reject(UUID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
        return repository.updateStatus(id , ApplicationStatus.REJECTED);
    }
    public JobApplication acceptOffer(UUID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }

        return repository.updateStatus(id , ApplicationStatus.OFFER);
    }
    public Map<ApplicationStatus , Long> getStatusSummary(){
        List<JobApplication> all = repository.findAll();
        return ApplicationStatistics.countByStatus(all);
    }
    public List<String> getTopCompanies(int limit){
        List<JobApplication> all = repository.findAll();
        return ApplicationStatistics.topCompanies(all,limit);
    }
    public long getActiveCount(){
        List<JobApplication> all = repository.findAll();
        return ApplicationStatistics.countByActive(all);
    }
    public long getFinalCount(){
        List<JobApplication> all = repository.findAll();
        return ApplicationStatistics.countFinal(all);
    }











    private void validateApplicationInput(String company , String role , LocalDate appliedDate , Money expectedSalary){
        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("company must be non blank");
        }
        if (role == null || role.isBlank()){
            throw new IllegalArgumentException("role must be non blank");
        }
        if (appliedDate == null){
            throw new IllegalArgumentException("appliedDate must not be null");
        }
        if (appliedDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("appliedDate must not be in the future");
        }
        if (expectedSalary == null){
            throw new IllegalArgumentException("expectedSalary must not be null");
        }
    }


}
