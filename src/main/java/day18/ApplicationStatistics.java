package day18;

import day05.ApplicationStatus;
import day05.JobApplication;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public final class ApplicationStatistics {
    private ApplicationStatistics(){}

    public static Map<ApplicationStatus, Long> countByStatus(List<JobApplication> applications){
        return applications == null ? Map.of() : applications.stream().collect(Collectors.groupingBy(JobApplication::status,Collectors.counting()));
    }

    public static Map<String , Long> countByCompany(List<JobApplication> applications){
        if (applications == null){
            return Map.of();
        }
        return applications.stream().collect(Collectors.groupingBy(app -> app.company().toLowerCase(Locale.ROOT) , Collectors.counting()));
    }
    public static List<String> topCompanies(List<JobApplication> applications , int limit){
        if (applications == null || limit <= 0){
            return List.of();
        }
        Map<String , Long> counts = countByCompany(applications);
        return counts.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey())).
                limit(limit).map(Map.Entry::getKey).toList();
    }

    public static long countByActive(List<JobApplication> applications){
        return nonNullCountActiveApplication(applications);
    }
    public static long countFinal(List<JobApplication> applications){

        return nonNullCountFinalApplication(applications);
    }

    private static long nonNullCountFinalApplication(List<JobApplication> applications){
        return applications == null ? 0L : applications.stream().filter(app -> !app.isActive()).count();
    }
    private static long nonNullCountActiveApplication(List<JobApplication> applications){
        return applications == null ? 0L : applications.stream().filter(JobApplication::isActive).count();
    }
}
