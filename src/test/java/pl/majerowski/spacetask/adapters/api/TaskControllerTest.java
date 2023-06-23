package pl.majerowski.spacetask.adapters.api;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.majerowski.spacetask.MongoTest;
import pl.majerowski.spacetask.task.adapters.api.TaskCreationRequest;
import pl.majerowski.spacetask.task.adapters.taskdb.TaskDocument;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.task.domain.model.TaskStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest extends MongoTest {

    private final static String USERNAME = "admin@admin.com";
    private final static String PASSWORD = "$2a$12$xYgb.Z6vaDWDer.Dwi3k2eyMDfBOeDxtga6o1GlOC/OhQGMUhQA/W";
//    private final static String PASSWORD = "password";
    private final static String TASK_ID = "6eb174occ";
    private String jwtToken;
    @Value(value = "${local.server.port}")
    private int port;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TestRestTemplate restTemplate;

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @BeforeEach
    void authenticate() {
        String authenticateUrl = "http://localhost:" + port + "/authenticate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", USERNAME);
        body.add("password", PASSWORD);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AuthResponse> response = restTemplate.exchange(
                authenticateUrl,
                HttpMethod.POST,
                requestEntity,
                AuthResponse.class
        );

        AuthResponse authResponse = response.getBody();
        jwtToken = authResponse.getToken();
    }

    @AfterEach
    void cleanup() {
        mongoTemplate.dropCollection(TaskDocument.class);
    }

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
