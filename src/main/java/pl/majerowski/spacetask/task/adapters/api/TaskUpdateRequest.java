package pl.majerowski.spacetask.task.adapters.api;

import lombok.Setter;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;

import java.time.Instant;

@Setter
class TaskUpdateRequest {
    private String id;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant timestamp;

    public Task asDomain(String email) {
        return new Task(
                this.id,
                email,
                this.name,
                this.description,
                this.status,
                this.timestamp
        );
    }
}
