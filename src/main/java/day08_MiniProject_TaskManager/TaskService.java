package day08_MiniProject_TaskManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    public Task addTask(String title, String description, LocalDate dueDate, TaskStatus status) {
        UUID id = UUID.randomUUID();
        Task task = new Task(title,description,dueDate,status,id);
        tasks.add(task);
        return task;

    }
    public List<Task> listTasks(){
        return List.copyOf(tasks);
    }
    public List<Task> findByStatus(TaskStatus status){
        if (status == null){
            throw new IllegalArgumentException("status must not be null.");
        }
        return tasks.stream().filter(tsk -> tsk.status() == status).toList();
    }
    public List<Task> findByTitle(String title){
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("title must be non-blank");
        }
        return tasks.stream().filter(tsk -> tsk.title().equalsIgnoreCase(title)).toList();
    }
    public Task updateStatus(UUID id, TaskStatus newStatus){
        validateIdStatus(id , newStatus);
        int index = -1;
        for (int i = 0 ; i < tasks.size() ; i++){
            Task tsk = tasks.get(i);
            if (tsk.id().equals(id)){
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("Task with id " + id + " not found.");
        }
        Task existing = tasks.get(index);
        Task updated = existing.withStatus(newStatus);
        tasks.set(index , updated);
        return updated;
    }
    public void deleteTask(UUID id){
        if (id == null){
            throw new IllegalArgumentException("id must not be null.");
        }
        int index = -1;
        for (int i = 0 ; i < tasks.size();i++ ){
            Task tsk = tasks.get(i);
            if (tsk.id().equals(id)){
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("Task with id " + id + " not found.");
        }
        Task existing = tasks.get(index);
        tasks.remove(existing);
    }
    public void clear(){
        tasks.clear();
    }
    private void validateIdStatus(UUID id , TaskStatus status){
        if (status == null){
            throw new IllegalArgumentException("status must not be null");
        }
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
    }
}


