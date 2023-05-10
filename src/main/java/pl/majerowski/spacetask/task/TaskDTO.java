package pl.majerowski.spacetask.task;

import lombok.Setter;

@Setter
public class TaskDTO {
    private String name;
    private String description;

    public Task asDomain() {
        return new Task (null, this.name, this.description, TaskStatus.TO_DO);
    }
}
