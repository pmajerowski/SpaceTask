package pl.majerowski.spacetask.adapters.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.majerowski.spacetask.MongoTest;
import pl.majerowski.spacetask.task.adapters.api.TaskCreationRequest;
import pl.majerowski.spacetask.task.adapters.taskdb.TaskDocument;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest extends MongoTest {

    private final static String TASK_ID = "6eb174occ";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void sampleData() {
        Task task = new Task(
                TASK_ID,
                "test task",
                "task to test",
                TaskStatus.IN_PROGRESS,
                Instant.now()
        );

        mongoTemplate.insert(TaskDocument.asDocument(task));
    }

    @AfterEach
    void cleanup() {
        mongoTemplate.dropCollection(TaskDocument.class);
    }

    @Test
    void shouldGetTaskById() {
        // when
        String url = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/tasks")
                .queryParam("taskId", TASK_ID)
                .toUriString();

        Task result = restTemplate.getForObject(url, Task.class);

        // then
        assertThat(result.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(result.getName()).isEqualTo("test task");
    }

    @Test
    void shouldAddTask() {
        // given
        TaskCreationRequest request = new TaskCreationRequest("test", "test");
        String url = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/tasks")
                .toUriString();

        // when
        restTemplate.postForObject(url, request, Void.class);

        // then
        List<TaskDocument> tasks = mongoTemplate.findAll(TaskDocument.class);
        assertThat(tasks).hasSize(2);
        assertThat(tasks)
                .allSatisfy(taskDocument -> assertThat(taskDocument.getStatus()).isEqualTo(TaskStatus.TO_DO));
    }

    @Test
    void shouldUpdateTask() {
        //testing github actions change
    }
}
