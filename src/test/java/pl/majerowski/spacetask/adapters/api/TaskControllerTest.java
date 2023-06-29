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
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.majerowski.spacetask.MongoTest;
import pl.majerowski.spacetask.task.adapters.api.AuthenticationController;
import pl.majerowski.spacetask.task.adapters.api.TaskCreationRequest;
import pl.majerowski.spacetask.task.adapters.dto.AuthenticationRequest;
import pl.majerowski.spacetask.task.adapters.taskdb.TaskDocument;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;
import pl.majerowski.spacetask.user.adapters.userdb.AppUserDocument;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest extends MongoTest {

    private final static String TASK_ID = "6eb174occ";
    private final static String USER_ID = "6eb174ouu";
    private final static String USER_EMAIL = "test@example.com";
    private static String token;

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

        AppUser appUser = new AppUser(
                USER_ID,
                USER_EMAIL,
                "test user",
                "$2a$12$ZSzVc3JG/60EWpAgNzzvre/gyeXJJ6XIw684ue2L0nm/9xq1LPCp2",
                Collections.emptyList(),
                true,
                true,
                true,
                true
        );

        mongoTemplate.insert(AppUserDocument.asDocument(appUser));

        String password = "password";

        HttpEntity<AuthenticationRequest> requestEntity = buildRequest(password);

        // when
        AuthenticationController.AuthenticationResponse response = restTemplate.exchange(
                "http://localhost:" + port + "/authenticate",
                HttpMethod.POST,
                requestEntity,
                AuthenticationController.AuthenticationResponse.class
        ).getBody();

        token = response.getToken();
    }

    @AfterEach
    void cleanup() {
        mongoTemplate.dropCollection(TaskDocument.class);
        mongoTemplate.dropCollection(AppUserDocument.class);
    }
//    @Disabled("working on adjusting security in tests")
    @Test
    void shouldGetTaskById() {
        // given
        String url = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/tasks")
                .queryParam("taskId", TASK_ID)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        // when
//        Task result = restTemplate.getForObject(url, Task.class);

        ResponseEntity<Task> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Task.class
        );

        // then
        assertThat(result.getBody().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(result.getBody().getName()).isEqualTo("test task");
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

    private static HttpEntity<AuthenticationRequest> buildRequest(String password) {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(USER_EMAIL, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(authenticationRequest, headers);
    }
}
