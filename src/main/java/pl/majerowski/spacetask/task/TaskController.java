package pl.majerowski.spacetask.task;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Task getTask(@RequestParam String name) {
        return taskRepository.findTaskByName(name);
    }

    @PostMapping
    public void postTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskDTO.asDomain();
        taskRepository.insert(task);
    }
}
