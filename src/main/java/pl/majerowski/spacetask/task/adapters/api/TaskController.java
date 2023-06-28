package pl.majerowski.spacetask.task.adapters.api;

import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping(path = "/all", produces = "application/json")
    public List<Task> getAll() {
        String email = getUserEmail();
        return taskService.findAllByEmail(email);
    }

    @GetMapping(produces = "application/json")
    public Task getTaskById(@RequestParam String taskId) {
        return taskService.findById(taskId);
    }

    @PostMapping(consumes = "application/json")
    public void postTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        String email = getUserEmail();
        Task task = taskCreationRequest.asDomain(email);
        taskService.insert(task);
    }

    @PutMapping(consumes = "application/json")
    public void updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        String email = getUserEmail();
        Task task = taskUpdateRequest.asDomain(email);
        taskService.update(task);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam String taskId) {
        taskService.delete(taskId);
    }

    private static String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
