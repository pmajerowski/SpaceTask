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
class TaskDocument {

    @Id
    private String id;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant timestamp;

    static Task asDomain(TaskDocument taskDocument) {
        return new Task(
                taskDocument.id,
                taskDocument.name,
                taskDocument.description,
                taskDocument.status,
                taskDocument.timestamp
        );
    }

    static TaskDocument asDocument(Task task) {
        return new TaskDocument(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getTimestamp()
        );
    }
}
