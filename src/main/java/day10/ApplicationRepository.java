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
    private final Map<UUID, JobApplication> byId = new HashMap<>();

    public ApplicationRepository(){
        for (ApplicationStatus status : ApplicationStatus.values()){
            byStatus.put(status , new ArrayList<>());
        }
    }
    public synchronized void add(JobApplication app){
        requireNonNull(app , "application");

        if (byId.containsKey(app.id())) {
            throw new DuplicateApplicationException("Application id already exists: " + app.id());
        }
        boolean alreadyExist = applications.stream().anyMatch(existing -> existing.company().equalsIgnoreCase(app.company()) && existing.role().equalsIgnoreCase(app.role()));
        if (alreadyExist){
            throw new DuplicateApplicationException("Application for " + app.role() + " at " + app.company() + " already exists");
        }

        applications.add(app);
        byId.put(app.id(), app);
        byStatus.get(app.status()).add(app);
        byCompany.computeIfAbsent(normalizeCompany(app.company()) , c -> new ArrayList<>()).add(app);
    }
    public synchronized List<JobApplication> findAll(){
        return List.copyOf(applications);
    }
    public synchronized List<JobApplication> findByCompany(String company){
        requireNonBlank(company , "company");
        List<JobApplication> list = byCompany.get(normalizeCompany(company));
        return copyOrEmpty(list);
    }
    public synchronized Optional<JobApplication> findFirstByCompany(String company){
        requireNonBlank(company , "company");
        return applications.stream().filter(app -> app.company().equalsIgnoreCase(company)).findFirst();
    }
    public synchronized List<JobApplication> findByStatus(ApplicationStatus status){
        requireNonNull(status , "status");
        List<JobApplication> list = byStatus.get(status);

        return copyOrEmpty(list);
    }
    public synchronized long countActive(){
        return applications.stream().filter(JobApplication::isActive).count();
    }

    public synchronized JobApplication findByIdOrThrow(UUID id){
        idNullCheck(id);
        return findById(id).
                orElseThrow(() -> new ApplicationNotFoundException("No application found with id: " + id));
    }
    public synchronized JobApplication updateStatus(UUID id , ApplicationStatus newStatus){
        idNullCheck(id);
        requireNonNull(newStatus , "newStatus");

        JobApplication existing = findByIdOrThrow(id);
        System.out.println("[DEBUG] [ApplicationRepository] Updating status | " +
                "id=" + id +
                ", from=" + existing.status() +
                ", to=" + newStatus);
        JobApplication updated = existing.withStatus(newStatus);

        int index = applications.indexOf(existing);
        applications.set(index , updated);
        byId.put(id, updated);

        byStatus.get(existing.status()).remove(existing);
        byStatus.get(updated.status()).add(updated);

        List<JobApplication> companyList = byCompany.get(normalizeCompany(existing.company()));
        int companyIndex = companyList.indexOf(existing);
        companyList.set(companyIndex , updated);
        System.out.println("[INFO] [ApplicationRepository] Updated status | " +
                "id=" + id +
                ", newStatus=" + updated.status());
        return updated;
    }

    public synchronized Optional<JobApplication> findById(UUID id){
        idNullCheck(id);
        return Optional.ofNullable(byId.get(id));
    }



    private static void requireNonNull(Object value, String fieldName){
        Objects.requireNonNull(value , fieldName + " must not be null");
    }
    private static void idNullCheck(UUID value){
        if (value == null){
            throw new IllegalArgumentException("id must not be null");
        }
    }
    private static List<JobApplication> copyOrEmpty(List<JobApplication> apps){
        return apps == null ? Collections.emptyList() : List.copyOf(apps);
    }

    private static String normalizeCompany(String company) {
        return company.trim().toLowerCase(Locale.ROOT);
    }

    private static void requireNonBlank(String s , String fieldName){
        if (s == null || s.isBlank()){
            throw new IllegalArgumentException(fieldName +  " must be non blank");
        }
    }

}
