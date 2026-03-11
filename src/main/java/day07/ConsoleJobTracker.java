package day07;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleJobTracker {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final JobTrackerService SERVICE = new JobTrackerService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> addApplicationFlow();
                case "2" -> listAllApplicationsFlow();
                case "3" -> listByStatusFlow();
                case "4" -> findByCompanyFlow();
                case "5" -> updateStatusFlow();
                case "0" -> running = false;
                default -> System.out.println("Unknown option. Please try again.");
            }
            System.out.println();
        }
        System.out.println("Goodbye");
    }

    private static void updateStatusFlow() {
        List<JobApplication> applications = SERVICE.listApplications();
        if (applications.isEmpty()) {
            System.out.println("No application available to update.");
        } else {
            printApplications(applications);
            UUID id = validateId();
            ApplicationStatus newStatus = validateStatus();
            JobApplication updated = SERVICE.updateStatus(id, newStatus);
            System.out.println("Status updated successfully to: " + updated.status());
        }
    }

    private static UUID validateId() {
        UUID id = null;
        while (id == null) {
            System.out.println("Enter the application id to update: ");
            String input = SCANNER.nextLine();
            try {
                id = UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input for id.");
            }
        }
        return id;
    }

    private static void findByCompanyFlow() {
        String companyName = stringValidation("Enter the company name: ", "company");
        List<JobApplication> application = SERVICE.findByCompany(companyName);
        if (application.isEmpty()) {
            System.out.println("No application was found for company: " + companyName);
        } else {
            JobApplication found = application.getFirst();
            List<JobApplication> singleResult = List.of(found);
            printApplications(singleResult);
        }
    }

    private static void listByStatusFlow() {
        ApplicationStatus status = validateStatus();
        List<JobApplication> applications = SERVICE.findByStatus(status);
        if (applications.isEmpty()) {
            System.out.println("No application found with status " + status);
        } else {
            printApplications(applications);
        }
    }

    private static void listAllApplicationsFlow() {
        List<JobApplication> applications = SERVICE.listApplications();
        if (applications.isEmpty()) {
            System.out.println("No application available.");
        } else {
            printApplications(applications);
        }
    }

    private static void addApplicationFlow() {
        String company = stringValidation("Enter the name of the company: ", "company");
        String role = stringValidation("Enter the role at the company: ", "role");
        LocalDate appliedDate = validateAppliedDate();
        Money expectedSalary = validateExpectedSalary();
        ApplicationStatus status = validateStatus();
        SERVICE.addApplication(company, role, status, appliedDate, expectedSalary);
        System.out.println("The application added successfully.");
    }

    private static void printMenu() {
        System.out.println("===== Console Job Tracker =====");
        System.out.println("1 - Add application");
        System.out.println("2 - List all applications");
        System.out.println("3 - List applications by status");
        System.out.println("4 - Find application by company");
        System.out.println("5 - Update application status");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");
    }

    private static int validateAppNumber(List<JobApplication> applications) {
        while (true) {
            System.out.println("Enter the application number: ");
            if (SCANNER.hasNextInt()) {
                int appNumber = SCANNER.nextInt();
                SCANNER.nextLine();
                if (appNumber >= 1 && appNumber <= applications.size()) {
                    return appNumber;
                } else {
                    System.out.println("Invalid application number. Please choose between 1 and " + applications.size());
                }
            } else {
                System.out.println("invalid input for appNumber.");
                SCANNER.nextLine();
            }
        }
    }

    private static void printApplications(List<JobApplication> applications) {
        int count = 1;
        for (JobApplication app : applications) {
            System.out.println("Application number : " + count);
            System.out.println("Company name: " + app.company());
            System.out.println("Role: " + app.role());
            System.out.println("Status: " + app.status());
            System.out.println("Applied date: " + app.appliedDate());
            System.out.println("ExpectedSalary: " + app.expectedSalary());
            System.out.println("ID : " + app.id());
            System.out.println("----------------------------------------------------");
            count++;
        }
    }

    private static ApplicationStatus validateStatus() {
        ApplicationStatus status = null;
        while (status == null) {
            System.out.println("Enter application status (APPLIED, INTERVIEWING, OFFER, REJECTED):");
            String input = SCANNER.nextLine().trim().toUpperCase();
            try {
                status = ApplicationStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status please try again.");
            }
        }
        return status;
    }

    private static LocalDate validateAppliedDate() {
        LocalDate appliedDate = null;
        while (appliedDate == null) {
            System.out.println("Enter the applied date");
            String input = SCANNER.nextLine().trim();
            try {
                appliedDate = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid applied date input. Please use format YYYY-MM-DD.");
            }
        }
        return appliedDate;
    }

    private static Money validateExpectedSalary() {
        long amount = amountValidation();
        String currency = stringValidation("Enter the currency of expected salary:  ", "currency");
        return new Money(amount, currency);
    }

    private static long amountValidation() {
        while (true) {
            System.out.println("Enter the amount of the expected salary: ");
            if (SCANNER.hasNextLong()) {
                long amount = SCANNER.nextLong();
                SCANNER.nextLine();
                if (amount > 0) {
                    return amount;
                }
                System.out.println("The amount must not be negative.");
            } else {
                System.out.println("invalid input for the amount.");
                SCANNER.nextLine();
            }
        }
    }

    private static String stringValidation(String prompt, String filedName) {
        String st = null;
        while (st == null) {
            System.out.println(prompt);
            String input = SCANNER.nextLine();
            if (input.isBlank()) {
                System.out.println(filedName + " must be non-blank");
            } else {
                st = input;
            }
        }
        return st;
    }
}
