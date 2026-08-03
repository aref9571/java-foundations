package day10;
import day05.JobApplication;

import java.time.LocalDate;
import java.util.*;
public final class ApplicationQueries {
    private ApplicationQueries(){}

    public static List<JobApplication> filterByTitleKeyword(List<JobApplication> applications , String keyword){
        List<JobApplication> apps = safeApplications(applications);
        String normalized = normalizeNonBlank(keyword , "keyword");

        return apps.stream().filter(app -> app.role() != null && app.role().toLowerCase(Locale.ROOT).contains(normalized)).toList();

    }

    public static List<JobApplication> filterAppliedAfter(List<JobApplication> applications , LocalDate date){
        List<JobApplication> apps = safeApplications(applications);
        if (date == null){
            throw new IllegalArgumentException("Date must not be null");
        }
        return apps.stream().filter(app -> app.appliedDate().isAfter(date)).toList();
    }

    public static List<JobApplication> filterByCompanyPrefix(List<JobApplication> applications , String prefix){
        List<JobApplication> apps = safeApplications(applications);
        String normalized = normalizeNonBlank(prefix , "prefix");
        return apps.stream().filter(app ->app.company() != null && app.company().toLowerCase(Locale.ROOT).startsWith(normalized)).toList();
    }

    private static List<JobApplication> safeApplications(List<JobApplication> applications){
        return applications == null ? Collections.emptyList() : applications;
    }

    private static String normalizeNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value.toLowerCase(Locale.ROOT);
    }
}
