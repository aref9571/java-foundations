package day26;

import day05.ApplicationStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record AnalyticsSnapshot (
        long totalApplications,
        long activeApplications,
        long finalApplications,
        Map<ApplicationStatus , Long> byStatus,
        List<String> topCompanies){
    public AnalyticsSnapshot{
        Objects.requireNonNull(byStatus , "byStatus must not be null");
        Objects.requireNonNull(topCompanies , "topCompanies must not be null");

        byStatus = Map.copyOf(byStatus);
        topCompanies = List.copyOf(topCompanies);
    }
}
