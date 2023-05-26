package pl.majerowski.spacetask.adapters.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import pl.majerowski.spacetask.MongoContainerInitializerTest;
import pl.majerowski.spacetask.task.adapters.api.TaskController;
import pl.majerowski.spacetask.task.adapters.taskdb.TaskDocument;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;

import java.time.Instant;
import java.util.List;

@SpringBootTest
class TaskControllerTest extends MongoContainerInitializerTest {

//    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    private TaskController taskController;
    @Autowired
    private MongoTemplate mongoTemplate;
//
//    @BeforeAll
//    static void contextLoads() {
//        mongoDBContainer.start();
//    }

//    @AfterAll
//    static void closeTestcontainers() {
//        mongoDBContainer.stop();
//    }

    @BeforeEach
    void sampleData() {
        Task task = new Task(
                "6eb174occ",
                "tast task",
                "task to test",
                TaskStatus.IN_PROGRESS,
                Instant.now()
        );
        mongoTemplate.insert(TaskDocument.asDocument(task));
    }

    @Test
    void shouldGetListOfAllTasks() {
        // given
        List<TaskDocument> taskDocuments = mongoTemplate.findAll(TaskDocument.class);
        // when
        System.out.println(taskDocuments);
        // then
    }

    @Test
    void shouldAddTask() {

    }

    @Test
    void shouldUpdateTask() {

    }

}
