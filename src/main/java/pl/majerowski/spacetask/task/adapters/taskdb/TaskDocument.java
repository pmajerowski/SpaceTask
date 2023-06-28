package pl.majerowski.spacetask.task.adapters.taskdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Document(collection = "tasks")
public class TaskDocument {

    @Id
    private String id;
    private String email;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant timestamp;

    public static Task asDomain(TaskDocument taskDocument) {
        return new Task(
                taskDocument.id,
                taskDocument.email,
                taskDocument.name,
                taskDocument.description,
                taskDocument.status,
                taskDocument.timestamp
        );
    }

    public static TaskDocument asDocument(Task task) {
        return new TaskDocument(
                task.getId(),
                task.getEmail(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getTimestamp()
        );
    }
}
