package day07;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JobTrackerService {

    private final List<JobApplication> applications = new ArrayList<>();

    public JobApplication addApplication(String company,
                                         String role,
                                         ApplicationStatus status,
                                         LocalDate appliedDate,
                                         Money expectedSalary) {
        validateRequiredFields(company, role, status, appliedDate, expectedSalary);
        UUID newId = UUID.randomUUID();
        JobApplication application = new JobApplication(company, role, status, appliedDate, expectedSalary, newId);
        applications.add(application);
        return application;
    }

    public JobApplication updateStatus(UUID id, ApplicationStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus must not be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }
        int foundIndex = -1;
        for (int i = 0; i < applications.size(); i++) {
            JobApplication app = applications.get(i);
            if (app.id().equals(id)) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex == -1) {
            throw new IllegalArgumentException("Application with id " + id + " not found.");
        }
        JobApplication existing = applications.get(foundIndex);
        JobApplication updated = existing.withStatus(newStatus);
        applications.set(foundIndex, updated);
        return updated;
    }

    public List<JobApplication> listApplications() {
        return List.copyOf(applications);
    }

    public List<JobApplication> findByStatus(ApplicationStatus status) {
        return applications.stream()
                .filter(app -> app.status() == status)
                .toList();
    }

    public Optional<JobApplication> findByCompany(String company) {
        return applications.stream()
                .filter(app -> app.company().equalsIgnoreCase(company))
                .findFirst();
    }

    public void clear() {
        applications.clear();
    }

    private static void validateRequiredFields(String company,
                                               String role,
                                               ApplicationStatus status,
                                               LocalDate appliedDate,
                                               Money expectedSalary) {
        if (company == null || company.isBlank()) {
            throw new IllegalArgumentException("company must be non-blank");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("role must be non-blank");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        if (appliedDate == null) {
            throw new IllegalArgumentException("appliedDate must not be null");
        }
        if (appliedDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("appliedDate must not be in the future");
        }
        if (expectedSalary == null) {
            throw new IllegalArgumentException("expectedSalary must not be null");
        }
    }
}
