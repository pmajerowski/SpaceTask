package pl.majerowski.spacetask.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Task {
    private Long Id;
    private String name;
    private String description;
    private TaskStatus status;

}
