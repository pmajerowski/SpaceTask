package pl.majerowski.spacetask.adapters.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import static org.assertj.core.api.Assertions.assertThat;


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
                "admin@admin.com",
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
    @Disabled("working on adjusting security in tests")
    @Test
    void shouldGetTaskById() {
        // given
        String url = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/tasks")
                .queryParam("taskId", TASK_ID)
                .toUriString();

        // when
        Task result = restTemplate.getForObject(url, Task.class);

        // then
        assertThat(result.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(result.getName()).isEqualTo("test task");
    }
    @Disabled("working on adjusting security in tests")
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
                .satisfiesOnlyOnce(taskDocument -> assertThat(taskDocument.getStatus()).isEqualTo(TaskStatus.TO_DO))
                .satisfiesOnlyOnce(taskDocument -> assertThat(taskDocument.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS));
    }
    @Disabled("working on adjusting security in tests")
    @Test
    void shouldUpdateTask() {
        // given
        String url = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/tasks")
                .queryParam("taskId", TASK_ID)
                .toUriString();
        String newName = "test updated name";
        String newDescription = "test updated description";

        TestTaskUpdateRequest toUpdate = new TestTaskUpdateRequest(
                TASK_ID,
                newName,
                newDescription
        );

        // when
        restTemplate.put(url, toUpdate);

        // then
        List<TaskDocument> tasks = mongoTemplate.findAll(TaskDocument.class);
        assertThat(tasks).hasSize(1);
        assertThat(tasks)
                .satisfiesOnlyOnce(taskDocument -> assertThat(taskDocument.getName()).isEqualTo(newName))
                .satisfiesOnlyOnce(taskDocument -> assertThat(taskDocument.getDescription()).isEqualTo(newDescription));
    }
    @Disabled("working on adjusting security in tests")
    @Test
    void shouldDeleteTask() {
        // given
        String url = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/tasks")
                .queryParam("taskId", TASK_ID)
                .toUriString();

        // when
        restTemplate.delete(url);

        //then
        List<TaskDocument> tasks = mongoTemplate.findAll(TaskDocument.class);
        assertThat(tasks).hasSize(0);
    }
}
