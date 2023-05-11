package pl.majerowski.spacetask.task.domain.ports;

import pl.majerowski.spacetask.task.domain.model.TaskStatus;
import pl.majerowski.spacetask.task.domain.model.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();

    Task findById(String taskId);

    void insert(Task task);

}
