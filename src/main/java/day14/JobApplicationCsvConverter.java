package day14;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;

import java.time.LocalDate;
import java.util.UUID;

public final class JobApplicationCsvConverter {
    private static final String NULL_VALUE = "~";

    private JobApplicationCsvConverter(){}

    public static String toLine(JobApplication app){
        if (app == null ){
            throw new IllegalArgumentException("app must not be null");
        }

        String company = app.company();
        String role = app.role();
        String status = app.status().name();
        String appliedDate = app.appliedDate().toString();
        String id = app.id().toString();

        String expectedSalaryCents;
        String currency;

        if (app.expectedSalary() == null){
            expectedSalaryCents = NULL_VALUE;
            currency = NULL_VALUE;
        }
        else {
            expectedSalaryCents = String.valueOf(app.expectedSalary().amountInCents());
            currency = app.expectedSalary().currency();
        }
        return String.join("|", id, escape(company), escape(role), status, appliedDate,
                expectedSalaryCents, escape(currency));
    }

    public static JobApplication fromLine(String line){
        if (line == null || line.isBlank()){
            throw new IllegalArgumentException("line must be non blank");
        }

        String[] parts = split(line);

        String idText = parts[0];
        String company = unescape(parts[1]);
        String role = unescape(parts[2]);
        String statusText = parts[3];
        String appliedDateText = parts[4];
        String expectedSalaryCentsText = parts[5];
        String currencyText = unescape(parts[6]);

        LocalDate appliedDate = LocalDate.parse(appliedDateText);
        UUID id = UUID.fromString(idText);
        ApplicationStatus status = ApplicationStatus.valueOf(statusText);
        Money expectedSalary;

        if (NULL_VALUE.equals(expectedSalaryCentsText) && NULL_VALUE.equals(currencyText)
                || "null".equals(expectedSalaryCentsText) && "null".equals(currencyText)) {
            expectedSalary = null;
        }
        else {
            if (NULL_VALUE.equals(expectedSalaryCentsText) || NULL_VALUE.equals(currencyText)) {
                throw new IllegalArgumentException("Salary amount and currency must both be present or absent");
            }
            long expectedSalaryCents = Long.parseLong(expectedSalaryCentsText);
            expectedSalary = new Money(expectedSalaryCents, currencyText);
        }
        return new JobApplication(company,role,status,appliedDate , expectedSalary,id);
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("|", "\\|")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static String unescape(String value) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            if (escaped) {
                result.append(switch (character) {
                    case 'n' -> '\n';
                    case 'r' -> '\r';
                    default -> character;
                });
                escaped = false;
            } else if (character == '\\') {
                escaped = true;
            } else {
                result.append(character);
            }
        }
        if (escaped) {
            throw new IllegalArgumentException("Invalid trailing escape character");
        }
        return result.toString();
    }

    private static String[] split(String line) {
        StringBuilder field = new StringBuilder();
        java.util.List<String> fields = new java.util.ArrayList<>();
        boolean escaped = false;
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (character == '|' && !escaped) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(character);
            }
            escaped = character == '\\' && !escaped;
            if (character != '\\') {
                escaped = false;
            }
        }
        if (escaped) {
            throw new IllegalArgumentException("Invalid trailing escape character");
        }
        fields.add(field.toString());
        if (fields.size() != 7) {
            throw new IllegalArgumentException("Expected 7 fields but got " + fields.size());
        }
        return fields.toArray(String[]::new);
    }
}
