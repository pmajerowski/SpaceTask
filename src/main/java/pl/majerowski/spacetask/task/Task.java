package pl.majerowski.spacetask.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String name;
    private String description;
    private TaskStatus status;

}
