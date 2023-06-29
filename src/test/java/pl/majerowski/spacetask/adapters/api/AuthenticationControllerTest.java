package pl.majerowski.spacetask.adapters.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.majerowski.spacetask.MongoTest;
import pl.majerowski.spacetask.task.adapters.dto.AuthenticationRequest;
import pl.majerowski.spacetask.task.adapters.taskdb.TaskDocument;
import pl.majerowski.spacetask.user.adapters.userdb.AppUserDocument;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.majerowski.spacetask.task.adapters.api.AuthenticationController.AuthenticationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest extends MongoTest {

    private final static String USER_ID = "6eb174ouu";
    private final static String USER_EMAIL = "test@example.com";
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void sampleData() {
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
    }

    @AfterEach
    void cleanup() {
        mongoTemplate.dropCollection(AppUserDocument.class);
    }

    @Test
    public void shouldReturnValidJwtFromAuthenticateEndpoint() {
        String password = "password";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(USER_EMAIL, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<>(authenticationRequest, headers);


        AuthenticationResponse response = restTemplate.exchange(
                "http://localhost:" + port + "/authenticate",
                HttpMethod.POST,
                requestEntity,
                AuthenticationResponse.class
        ).getBody();

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotEmpty();

    }

}
