package pl.majerowski.spacetask.task.adapters.api;

import org.springframework.web.bind.annotation.*;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.ports.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @GetMapping
    public Task getTaskById(@RequestParam String id) {
        return taskService.findById(id);
    }

    @PostMapping
    public void postTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        Task task = taskCreationRequest.asDomain();
        taskService.insert(task);
    }
}
