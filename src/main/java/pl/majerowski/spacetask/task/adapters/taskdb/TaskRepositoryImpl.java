package pl.majerowski.spacetask.task.adapters.taskdb;

import org.springframework.stereotype.Repository;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.ports.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskMongoRepository taskMongoRepository;

    public TaskRepositoryImpl(TaskMongoRepository taskMongoRepository) {
        this.taskMongoRepository = taskMongoRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskMongoRepository.findAll()
                .stream()
                .map(TaskDocument::asDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Task findById(String taskId) {
        return taskMongoRepository.findById(taskId).map(TaskDocument::asDomain).orElseThrow();
    }

    @Override
    public void insert(Task task) {
        taskMongoRepository.insert(TaskDocument.asDocument(task));
    }

    @Override
    public void delete(String taskId) {
        taskMongoRepository.deleteById(taskId);
    }

}
