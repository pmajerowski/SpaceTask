package pl.majerowski.spacetask.task.adapters.api;

import lombok.Setter;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;

import java.time.Instant;

@Setter
public class TaskUpdateRequest {
    private String id;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant timestamp;

    public Task asDomain() {
        return new Task(
                this.id,
                this.name,
                this.description,
                this.status,
                this.timestamp
        );
    }
}
