package day05;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class JobApplicationBuilder {
    private String company = "DefaultCorp";
    private String role = "Backend Engineer";
    private ApplicationStatus status = ApplicationStatus.APPLIED;
    private LocalDate appliedDate = LocalDate.of(2026 , 1 , 1);
    private Money expectedSalary = new Money(1000L , "EUR");
    private UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public static JobApplicationBuilder aDefaultApplication(){
        return new JobApplicationBuilder();
    }

    public JobApplicationBuilder withCompany(String company){
        this.company = company;
        return this;
    }

    public JobApplicationBuilder withRole(String role){
        this.role = role;
        return this;
    }

    public JobApplicationBuilder withStatus(ApplicationStatus status){
        this.status = status;
        return this;
    }

    public JobApplicationBuilder withAppliedDate(LocalDate appliedDate){
        this.appliedDate = appliedDate;
        return this;
    }

    public JobApplicationBuilder withExpectedSalary(Money expectedSalary){
        this.expectedSalary = expectedSalary;
        return this;
    }

    public JobApplicationBuilder withId(UUID id){
        this.id = id;
        return this;
    }
    public JobApplication build(){
        return new JobApplication(company,role,status,appliedDate,expectedSalary,id);
    }

}
