package day05;

import java.time.LocalDate;
import java.util.Objects;

public final class JobApplication {

    private final String company;
    private final String role;
    private final ApplicationStatus status;
    private final LocalDate appliedDate;
    private final Money expectedSalary;

    public JobApplication(String company,
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

        this.company = company;
        this.role = role;
        this.status = status;
        this.appliedDate = appliedDate;
        this.expectedSalary = expectedSalary;
    }

    public String company() {
        return company;
    }

    public String role() {
        return role;
    }

    public ApplicationStatus status() {
        return status;
    }

    public LocalDate appliedDate() {
        return appliedDate;
    }

    public Money expectedSalary() {
        return expectedSalary;
    }

    public boolean isActive() {
        return status == ApplicationStatus.APPLIED
                || status == ApplicationStatus.INTERVIEWING;
    }

    public JobApplication withStatus(ApplicationStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus must not be null");
        }
        return new JobApplication(
                this.company,
                this.role,
                newStatus,
                this.appliedDate,
                this.expectedSalary
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobApplication)) {
            return false;
        }
        JobApplication that = (JobApplication) o;
        return company.equals(that.company)
                && role.equals(that.role)
                && status == that.status
                && appliedDate.equals(that.appliedDate)
                && Objects.equals(expectedSalary, that.expectedSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, role, status, appliedDate, expectedSalary);
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "company='" + company + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                ", appliedDate=" + appliedDate +
                ", expectedSalary=" + expectedSalary +
                '}';
    }
}
