package pl.majerowski.spacetask.task.domain.ports;

import org.springframework.stereotype.Service;
import pl.majerowski.spacetask.task.domain.model.Task;

import java.util.List;

@Service
public final class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(String taskId) {
        return taskRepository.findById(taskId);
    }

    public void insert(Task task) {
        taskRepository.insert(task);
    }

}
