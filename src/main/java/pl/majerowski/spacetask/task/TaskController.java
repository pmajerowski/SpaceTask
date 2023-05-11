package pl.majerowski.spacetask.task;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/all")
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GetMapping
    public Task getTaskByName(@RequestParam String name) {
        return taskRepository.findTaskByName(name);
    }

    @GetMapping
    public Optional<Task> getTaskById(@RequestParam String id) {
        return taskRepository.findById(id);
    }

    @PostMapping
    public void postTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskDTO.asDomain();
        taskRepository.insert(task);
    }
}
