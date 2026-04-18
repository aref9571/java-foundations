package day14;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;

import java.time.LocalDate;
import java.util.UUID;

public final class JobApplicationCsvConverter {
    private JobApplicationCsvConverter(){};

    public static String toLine(JobApplication app){
        String company = app.company();
        String role = app.role();
        String status = app.status().name();
        String appliedDate = app.appliedDate().toString();
        String id = app.id().toString();

        String expectedSalaryCents;
        String currency;

        if (app.expectedSalary() == null){
            expectedSalaryCents = "null";
            currency = "null";
        }
        else {
            expectedSalaryCents = String.valueOf(app.expectedSalary().amountInCents());
            currency = app.expectedSalary().currency();
        }
        return String.join("|" , id , company , role , status , appliedDate ,expectedSalaryCents ,  currency);
    }

    public static JobApplication fromLine(String line){
        if (line == null || line.isBlank()){
            throw new IllegalArgumentException("line must be non blank");
        }

        String[] parts = line.split("\\|" , -1);
        if (parts.length != 7){
            throw new IllegalArgumentException("Expected 7 fields but got " + parts.length);
        }

        String idText = parts[0];
        String company = parts[1];
        String role = parts[2];
        String statusText = parts[3];
        String appliedDateText = parts[4];
        String expectedSalaryCentsText = parts[5];
        String currencyText = parts[6];

        LocalDate appliedDate = LocalDate.parse(appliedDateText);
        UUID id = UUID.fromString(idText);
        ApplicationStatus status = ApplicationStatus.valueOf(statusText);
        Money expectedSalary;

        if ("null".equals(expectedSalaryCentsText) || "null".equals(currencyText)){
            expectedSalary = null;
        }
        else {
            long expectedSalaryCents = Long.parseLong(expectedSalaryCentsText);
            expectedSalary = new Money(expectedSalaryCents, currencyText);
        }
        return new JobApplication(company,role,status,appliedDate , expectedSalary,id);
    }
}
