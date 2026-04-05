package day09;

import day05.ApplicationStatus;
import day05.JobApplication;

import java.util.*;

public class ApplicationIndex {
    private final List<JobApplication> applications = new ArrayList<>();
    private final Map<String , List<JobApplication>> appsByCompany = new HashMap<>();

    public void add(JobApplication app){
        if (app == null){
            throw new IllegalArgumentException("JobApplication must not be null");
        }
        applications.add(app);
        appsByCompany.computeIfAbsent(app.company() , c -> new ArrayList<>()).add(app);
    }

    public List<JobApplication> all(){
        return Collections.unmodifiableList(applications);
    }
    public List<JobApplication> findByCompany(String company){
        List<JobApplication> list = appsByCompany.get(company);
        if (list == null){
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }
    public Map<ApplicationStatus , Long> countByStatus(){
        Map<ApplicationStatus , Long> counts = new HashMap<>();
        for (JobApplication app : applications){
            ApplicationStatus status = app.status();
            Long current = counts.getOrDefault(status , 0L);
            counts.put(status , current + 1);
        }
        return counts;
    }
}
