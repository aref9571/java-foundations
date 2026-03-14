package day08_MiniProject_TaskManager;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public record Task(
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        UUID id
) {

    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must be non-blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must be non-blank.");
        }
        Objects.requireNonNull(dueDate, "dueDate must not be null");
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(id, "id must not be null");
    }

    public Task withStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus must not be null.");
        }
        return new Task(title, description, dueDate, newStatus, id);
    }

    public boolean isActive() {
        return status == TaskStatus.IN_PROGRESS;
    }
}
