package pl.majerowski.spacetask.task_container;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.majerowski.spacetask.task.Task;

import java.util.List;

@AllArgsConstructor
@Getter
@Document(collection = "task_containers")
public class TaskContainer {
    @Id
    private String id;
    private String name;
    private List<Task> tasks;
}
