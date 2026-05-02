package day10;

import day05.ApplicationStatus;
import day05.JobApplication;
import day11.ApplicationNotFoundException;
import day11.DuplicateApplicationException;

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

        boolean alreadyExist = applications.stream().anyMatch(existing -> existing.company().equalsIgnoreCase(app.company()) && existing.role().equalsIgnoreCase(app.role()));
        if (alreadyExist){
            throw new DuplicateApplicationException("Application for " + app.role() + " at " + app.company() + " already exists");
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
    public Optional<JobApplication> findFirstByCompany(String company){
        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("company must not be blank");
        }
        return applications.stream().filter(app -> app.company().equalsIgnoreCase(company)).findFirst();
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

    public JobApplication findByIdOrThrow(UUID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
        return findById(id).
                orElseThrow(() -> new ApplicationNotFoundException("No application found with id: " + id));
    }
    public JobApplication updateStatus(UUID id , ApplicationStatus newStatus){
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
        if (newStatus == null){
            throw new IllegalArgumentException("newStatus must not be null");
        }

        JobApplication existing = findByIdOrThrow(id);
        System.out.println("[DEBUG] [ApplicationRepository] Updating status | " +
                "id=" + id +
                ", from=" + existing.status() +
                ", to=" + newStatus);
        JobApplication updated = existing.withStatus(newStatus);

        int index = applications.indexOf(existing);
        applications.set(index , updated);

        byStatus.get(existing.status()).remove(existing);
        byStatus.get(updated.status()).add(updated);

        List<JobApplication> companyList = byCompany.get(existing.company());
        int companyIndex = companyList.indexOf(existing);
        companyList.set(companyIndex , updated);
        System.out.println("[INFO] [ApplicationRepository] Updated status | " +
                "id=" + id +
                ", newStatus=" + updated.status());
        return updated;
    }

    public Optional<JobApplication> findById(UUID id){
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return applications.stream().filter(app -> app.id().equals(id)).findFirst();
    }

}
