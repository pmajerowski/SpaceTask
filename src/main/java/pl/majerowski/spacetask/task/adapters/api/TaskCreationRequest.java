package pl.majerowski.spacetask.task.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;
import pl.majerowski.spacetask.task.domain.model.Task;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreationRequest {
    private String name;
    private String description;

    public Task asDomain() {
        return new Task (null, this.name, this.description, TaskStatus.TO_DO, Instant.now());
    }
}
