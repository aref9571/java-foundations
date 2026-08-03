package day26;

import day05.ApplicationStatus;
import day05.JobApplication;
import day10.ApplicationRepository;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsService {
    private final ApplicationRepository repository;

    public AnalyticsService(ApplicationRepository repository){
        this.repository = Objects.requireNonNull(repository , "repository must not be null");
    }

    public AnalyticsSnapshot computeSnapShot(){
        List<JobApplication> apps = repository.findAll();
        long total = apps.size();
        long active = apps.stream().filter(JobApplication::isActive).count();
        long fin = total - active;
        Map<ApplicationStatus , Long> byStatus = apps.stream()
                .collect(Collectors.groupingBy(
                        JobApplication::status ,
                        Collectors.counting()));
        List<String> topCompanies = apps.stream()
                .collect(Collectors.groupingBy(
                        app ->
                                app.company().toLowerCase(Locale.ROOT) ,
                        Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String ,
                        Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
        return new AnalyticsSnapshot(total , active ,fin ,byStatus ,topCompanies);
    }

    public Optional<JobApplication> mostRecentActive(){
        return repository.findAll().stream()
                .filter(JobApplication::isActive)
                .max(Comparator.comparing(JobApplication::appliedDate));
    }
}
