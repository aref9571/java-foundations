package day10;

import day05.ApplicationStatus;
import day05.JobApplication;
import java.util.*;

public class ApplicationRepository {
    private final List<JobApplication> applications = new ArrayList<>();
    private final Map<String , List<JobApplication>> byCompany = new HashMap<>();
    private final Map<ApplicationStatus , List<JobApplication>> byStatus = new EnumMap<>(ApplicationStatus.class);

    public ApplicationRepository(){
        for (ApplicationStatus status : ApplicationStatus.values()){
            byStatus.put(status , new ArrayList<>());
        }
    }
    public void add(JobApplication app){
        if (app == null){
            throw new IllegalArgumentException("JobApplication must not be null");
        }
        applications.add(app);
        byStatus.get(app.status()).add(app);
        byCompany.computeIfAbsent(app.company() , c -> new ArrayList<>()).add(app);
    }
    public List<JobApplication> findAll(){
        return List.copyOf(applications);
    }
    public List<JobApplication> findByCompany(String company){
        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("Company must be non blank");
        }
        List<JobApplication> list = byCompany.get(company);
        if (list == null){
            return Collections.emptyList();
        }
        return List.copyOf(list);
    }
    public List<JobApplication> findByStatus(ApplicationStatus status){
        if (status == null){
            throw new IllegalArgumentException("Status must not be null");
        }
        List<JobApplication> list = byStatus.get(status);
        if (list == null ){
            return Collections.emptyList();
        }
        return List.copyOf(list);
    }
    public long countActive(){
        return applications.stream().filter(JobApplication::isActive).count();
    }

}
