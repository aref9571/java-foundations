package day08_MiniProject_TaskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setup() {
        service = new TaskService();
        service.clear();
    }

    @Test
    void addTaskStoresAndListAll() {
        String title = "Java";
        String description = "Learn OOP";
        LocalDate dueDate = LocalDate.now();
        TaskStatus status = TaskStatus.TODO;

        Task myTask = service.addTask(title, description, dueDate, status);
        List<Task> allTasks = service.listTasks();

        assertEquals(1, allTasks.size());
        Task stored = allTasks.get(0);

        assertEquals(title, stored.title());
        assertEquals(description, stored.description());
        assertEquals(dueDate, stored.dueDate());
        assertEquals(status, stored.status());
        assertEquals(myTask.id(), stored.id());
    }

    @Test
    void findByStatus_returnOnlyMatchingStatus() {
        service.addTask("Java", "Learn OOP", LocalDate.now(), TaskStatus.TODO);
        service.addTask("Python", "Learn OOP", LocalDate.now(), TaskStatus.TODO);
        service.addTask("Golang", "Learn concurrency", LocalDate.now(), TaskStatus.DONE);

        List<Task> allTasks = service.listTasks();
        assertEquals(3, allTasks.size());

        List<Task> found = service.findByStatus(TaskStatus.TODO);
        assertEquals(2, found.size());

        for (Task tsk : found) {
            assertEquals(TaskStatus.TODO, tsk.status());
        }

        List<String> titles = found.stream().map(Task::title).toList();
        assertTrue(titles.contains("Java"));
        assertTrue(titles.contains("Python"));
        assertFalse(titles.contains("Golang"));
    }

    @Test
    void findByStatus_whenNoMatch_returnsEmpty() {
        service.addTask("Java", "Learn OOP", LocalDate.now(), TaskStatus.DONE);

        List<Task> found = service.findByStatus(TaskStatus.TODO);

        assertTrue(found.isEmpty());
    }

    @Test
    void findByTitle_returnsAllTasksWithThatTitle() {
        service.addTask("Java", "Learn OOP", LocalDate.now(), TaskStatus.DONE);
        service.addTask("Java", "Learn syntaxes", LocalDate.now(), TaskStatus.TODO);
        service.addTask("Python", "Learn basics", LocalDate.now(), TaskStatus.TODO);

        List<Task> found = service.findByTitle("Java");

        assertEquals(2, found.size());
        for (Task tsk : found) {
            assertEquals("Java", tsk.title());
        }
    }

    @Test
    void findByTitle_isCaseInsensitive() {
        service.addTask("JAVA", "Learn OOP", LocalDate.now(), TaskStatus.DONE);
        service.addTask("java", "Learn syntaxes", LocalDate.now(), TaskStatus.TODO);

        List<Task> foundLower = service.findByTitle("java");
        List<Task> foundUpper = service.findByTitle("JAVA");

        assertEquals(2, foundLower.size());
        assertEquals(2, foundUpper.size());
    }

    @Test
    void findByTitle_whenTitleDoesNotExist_returnsEmpty() {
        service.addTask("JAVA", "Learn OOP", LocalDate.now(), TaskStatus.DONE);
        service.addTask("java", "Learn syntaxes", LocalDate.now(), TaskStatus.TODO);

        List<Task> found = service.findByTitle("Python");

        assertTrue(found.isEmpty());
    }

    @Test
    void updateStatus_changeStatusForExistingTask() {
        Task myTask = service.addTask("JAVA", "Learn OOP", LocalDate.now(), TaskStatus.DONE);

        Task updated = service.updateStatus(myTask.id(), TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, updated.status());
        assertEquals(myTask.id(), updated.id());

        List<Task> allTasks = service.listTasks();
        assertEquals(1, allTasks.size());
        assertEquals(TaskStatus.IN_PROGRESS, allTasks.get(0).status());
    }

    @Test
    void updateStatus_throwsWhenIdNotFound() {
        UUID unknownId = UUID.randomUUID();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus(unknownId, TaskStatus.DONE)
        );

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void updateStatus_throwsWhenIdIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus(null, TaskStatus.DONE)
        );

        assertTrue(ex.getMessage().contains("id must not be null"));
    }

    @Test
    void updateStatus_throwsWhenNewStatusIsNull() {
        Task myTask = service.addTask("Java", "Learn OOP", LocalDate.now(), TaskStatus.TODO);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus(myTask.id(), null)
        );

        assertTrue(ex.getMessage().contains("status must not be null"));
    }

    @Test
    void deleteTask_removesTask() {
        Task task1 = service.addTask("Java", "Learn OOP", LocalDate.now(), TaskStatus.TODO);
        Task task2 = service.addTask("Python", "Learn basics", LocalDate.now(), TaskStatus.TODO);

        service.deleteTask(task1.id());

        List<Task> allTasks = service.listTasks();
        assertEquals(1, allTasks.size());
        assertEquals(task2.id(), allTasks.get(0).id());
    }

    @Test
    void deleteTask_throwsWhenIdNotFound() {
        service.addTask("Java", "Learn OOP", LocalDate.now(), TaskStatus.TODO);
        UUID unknownId = UUID.randomUUID();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteTask(unknownId)
        );

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void deleteTask_throwsWhenIdIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.deleteTask(null)
        );

        assertTrue(ex.getMessage().contains("id must not be null"));
    }
}
