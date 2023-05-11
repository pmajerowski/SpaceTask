package pl.majerowski.spacetask.task.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task {

    private String id;
    private String name;
    private String description;
    private TaskStatus status;

}
