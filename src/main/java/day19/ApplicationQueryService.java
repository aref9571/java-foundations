package day19;

import day05.JobApplication;
import day10.ApplicationRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ApplicationQueryService {

    private final ApplicationRepository repository;

    public ApplicationQueryService(ApplicationRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
    }

    public List<JobApplication> findAppliedInLastDays(int days) {
        if (days < 0) {
            throw new IllegalArgumentException("days must not be negative");
        }

        LocalDate threshold = LocalDate.now().minusDays(days);

        return all().stream()
                .filter(app -> !app.appliedDate().isBefore(threshold))
                .sorted(Comparator.comparing(JobApplication::appliedDate).reversed())
                .toList();
    }

    public List<JobApplication> searchByRoleKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("keyword must be non blank");
        }

        String normalized = keyword.toLowerCase(Locale.ROOT);

        return all().stream()
                .filter(app -> app.role() != null)
                .filter(app -> app.role().toLowerCase(Locale.ROOT).contains(normalized))
                .sorted(Comparator.comparing(JobApplication::company)
                        .thenComparing(JobApplication::role))
                .toList();
    }

    public List<JobApplication> findStuckApplications(int daysWithoutChange) {
        if (daysWithoutChange <= 0) {
            throw new IllegalArgumentException("daysWithoutChange must be positive");
        }

        LocalDate cutoff = LocalDate.now().minusDays(daysWithoutChange);

        return all().stream()
                .filter(app -> app.appliedDate() != null)
                .filter(app -> isStuck(app, cutoff))
                .sorted(Comparator.comparing(JobApplication::appliedDate))
                .toList();
    }

    private boolean isStuck(JobApplication app, LocalDate cutoff) {
        return app.isActive() && app.appliedDate().isBefore(cutoff);
    }

    private List<JobApplication> all() {
        return repository.findAll();
    }
}