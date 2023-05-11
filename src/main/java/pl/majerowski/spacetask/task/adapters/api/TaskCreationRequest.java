package pl.majerowski.spacetask.task.adapters.api;

import lombok.Setter;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;
import pl.majerowski.spacetask.task.domain.model.Task;

@Setter
public class TaskCreationRequest {
    private String name;
    private String description;

    public Task asDomain() {
        return new Task (null, this.name, this.description, TaskStatus.TO_DO);
    }
}
