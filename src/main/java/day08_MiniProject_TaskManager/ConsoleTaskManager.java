package day08_MiniProject_TaskManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleTaskManager {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final TaskService SERVICE = new TaskService();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> addTaskFlow();
                case "2" -> listAllTaskFlow();
                case "3" -> listByStatusFlow();
                case "4" -> listByTitleFlow();
                case "5" -> updateStatusFlow();
                case "6" -> deleteTaskFlow();
                case "0" -> running = false;
                default -> System.out.println("Unknown option. Please try again.");
            }
            System.out.println();
        }
        System.out.println("GoodBye");
    }

    private static void addTaskFlow() {
        String title = validateString("Enter the title of the task: ", "title");
        String description = validateString("Enter the description of the task: ", "description");
        LocalDate dueDate = validateDueDate();
        TaskStatus status = validateStatus();
        SERVICE.addTask(title, description, dueDate, status);
        System.out.println("Task \"" + title + "\" was added successfully.");
    }

    private static void listAllTaskFlow() {
        List<Task> allTasks = SERVICE.listTasks();
        if (allTasks.isEmpty()) {
            System.out.println("No tasks.");
        } else {
            printTasks(allTasks);
        }
    }

    private static void listByStatusFlow() {
        TaskStatus status = validateStatus();
        List<Task> found = SERVICE.findByStatus(status);
        if (found.isEmpty()) {
            System.out.println("No tasks with status " + status + ".");
        } else {
            printTasks(found);
        }
    }

    private static void listByTitleFlow() {
        String title = validateString("Enter the title of the task: ", "title");
        List<Task> found = SERVICE.findByTitle(title);
        if (found.isEmpty()) {
            System.out.println("No tasks with title \"" + title + "\".");
        } else {
            printTasks(found);
        }
    }

    private static void updateStatusFlow() {
        List<Task> allTasks = SERVICE.listTasks();
        if (allTasks.isEmpty()) {
            System.out.println("No tasks.");
        } else {
            printTasks(allTasks);
            UUID id = validateId();
            TaskStatus newStatus = validateStatus();
            Task updated = SERVICE.updateStatus(id, newStatus);
            System.out.println("Task \"" + updated.title() + "\" was successfully updated to " + updated.status() + ".");
        }
    }

    private static void deleteTaskFlow() {
        List<Task> allTasks = SERVICE.listTasks();
        if (allTasks.isEmpty()) {
            System.out.println("No tasks.");
        } else {
            printTasks(allTasks);
            UUID id = validateId();
            SERVICE.deleteTask(id);
            System.out.println("Task with ID " + id + " was successfully deleted.");
        }
    }

    private static UUID validateId() {
        while (true) {
            System.out.println("Enter the task ID: ");
            String input = SCANNER.nextLine().trim();
            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input for ID. Please use a valid UUID.");
            }
        }
    }

    private static void printTasks(List<Task> tasks) {
        int counter = 1;
        for (Task task : tasks) {
            System.out.println("Task number " + counter + ":");
            System.out.println("Title       : " + task.title());
            System.out.println("Description : " + task.description());
            System.out.println("Due date    : " + task.dueDate());
            System.out.println("Status      : " + task.status());
            System.out.println("ID          : " + task.id());
            System.out.println("--------------------------------------");
            counter++;
        }
    }

    private static TaskStatus validateStatus() {
        while (true) {
            System.out.println("Enter the status of the task " + Arrays.toString(TaskStatus.values()) + ": ");
            String status = SCANNER.nextLine().trim().toUpperCase();
            try {
                return TaskStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please use one of " + Arrays.toString(TaskStatus.values()));
            }
        }
    }

    private static LocalDate validateDueDate() {
        while (true) {
            System.out.println("Enter the due date (yyyy-MM-dd): ");
            String dueDate = SCANNER.nextLine().trim();
            try {
                return LocalDate.parse(dueDate, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }

    private static String validateString(String prompt, String fieldName) {
        while (true) {
            System.out.println(prompt);
            String respond = SCANNER.nextLine();
            if (respond != null && !respond.isBlank()) {
                return respond;
            }
            System.out.println(fieldName + " must be non-blank.");
        }
    }

    private static void printMenu() {
        System.out.println("===== Console Task Manager =====");
        System.out.println("1 - Add task");
        System.out.println("2 - List all tasks");
        System.out.println("3 - List tasks by status");
        System.out.println("4 - List tasks by title");
        System.out.println("5 - Update task status");
        System.out.println("6 - Delete task by ID");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");
    }
}
