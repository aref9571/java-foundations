package day17;

import day05.ApplicationStatus;
import day05.JobApplication;

import java.util.List;
import java.util.Objects;

public class ApplicationStreamQueries {

    public static List<JobApplication> filterByCompany(List<JobApplication> applications , String company){
        Objects.requireNonNull(applications , "applications must not be null");

        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("company must be non blank");
        }

        return applications.stream().filter(Objects::nonNull)
                .filter(app -> app.company() != null && app.company()
                        .equalsIgnoreCase(company)).toList();
    }
    
    public static List<JobApplication> filterByStatus(List<JobApplication> applications , ApplicationStatus status){
        Objects.requireNonNull(applications , "applications must not be null");
        if (status == null){
            throw new IllegalArgumentException("status must not be null");
        }
        return applications.stream().filter(Objects::nonNull)
                .filter(app -> app.status() != null && app.status() == status).toList();
    }
    public static List<String> listCompaniesSortedUnique(List<JobApplication> applications) {
        Objects.requireNonNull(applications, "applications must not be null");
        return applications.stream().filter(Objects::nonNull)
                .map(JobApplication::company).filter(Objects::nonNull).
                filter(company ->!company.isBlank()).map(String::toUpperCase)
                .distinct().sorted().toList();
    }

    public static long countByStatus(List<JobApplication> applications , ApplicationStatus status){
        Objects.requireNonNull(applications , "applications must not be null");
        if (status == null){
            throw new IllegalArgumentException("status must not be null");
        }
        return applications.stream().filter(Objects::nonNull)
                .filter(app -> app.status() == status).count();
    }
}
