package day10;
import day05.JobApplication;

import java.time.LocalDate;
import java.util.*;
public final class ApplicationQueries {
    private ApplicationQueries(){}

    public static List<JobApplication> filterByTitleKeyword(List<JobApplication> applications , String keyword){
        if (applications == null){
            return Collections.emptyList();
        }
        if (keyword == null || keyword.isBlank()){
            throw new IllegalArgumentException("Keyword must be non blank");
        }
        String normalized = keyword.toLowerCase(Locale.ROOT);

        return applications.stream().filter(app -> app.role() != null && app.role().toLowerCase(Locale.ROOT).contains(normalized)).toList();

    }

    public static List<JobApplication> filterAppliedAfter(List<JobApplication> applications , LocalDate date){
        if (applications == null){
            return Collections.emptyList();
        }
        if (date == null){
            throw new IllegalArgumentException("Date must not be null");
        }
        return applications.stream().filter(app -> app.appliedDate().isAfter(date)).toList();
    }

    public static List<JobApplication> filterByCompanyPrefix(List<JobApplication> applications , String prefix){
        if (applications == null){
            return Collections.emptyList();
        }
        if (prefix == null || prefix.isBlank()){
            throw new IllegalArgumentException("Prefix must be non blank");
        }
        String normalize = prefix.toLowerCase(Locale.ROOT);
        return applications.stream().filter(app -> app.company().toLowerCase(Locale.ROOT).startsWith(normalize)).toList();
    }
}
