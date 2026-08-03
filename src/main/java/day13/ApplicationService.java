package day13;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;
import day10.ApplicationRepository;
import day18.ApplicationStatistics;

import java.util.*;
import java.time.LocalDate;

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
        requireNonBlank(company , "company");
        return repository.findByCompany(company);
    }
    public Optional<JobApplication> findApplicationByCompany(String company){
        requireNonBlank(company, "company");
        return repository.findFirstByCompany(company);
    }
    public List<JobApplication> findByStatus(ApplicationStatus status){
        requireNonNull(status , "status");
        return repository.findByStatus(status);
    }
    public JobApplication moveToInterviewing(UUID id){
        requireNonNull(id , "id");
        System.out.println("[DEBUG] [ApplicationService] Moving to INTERVIEWING | id=" + id);
        JobApplication updated = repository.updateStatus(id , ApplicationStatus.INTERVIEWING);
        System.out.println("[INFO] [ApplicationService] Moved to INTERVIEWING | id=" + id +
                ", newStatus=" + updated.status());
        return updated;
    }
    public JobApplication reject(UUID id){
       requireNonNull(id , "id");
        return repository.updateStatus(id , ApplicationStatus.REJECTED);
    }
    public JobApplication acceptOffer(UUID id){
        requireNonNull(id , "id");

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
    public Optional<JobApplication> findMostRecentActive(){
        return repository.findAll().stream().filter(JobApplication::isActive).max(Comparator.comparing(JobApplication::appliedDate));
    }


    private void validateApplicationInput(String company , String role , LocalDate appliedDate , Money expectedSalary){
        requireNonBlank(company , "company");
        requireNonBlank(role , "role");
        requireNonNull(appliedDate , "appliedDate");
        if (appliedDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("appliedDate must not be in the future");
        }
        requireNonNull(expectedSalary , "expectedSalary");
    }


    private static void requireNonNull(Object value, String fieldName){
        if (value == null){
            throw new IllegalArgumentException(fieldName + " must be not be null");
        }
    }

    private static void requireNonBlank(String s , String fieldName){
        if (s == null || s.isBlank()){
            throw new IllegalArgumentException(fieldName +  " must be non blank");
        }
    }
}
