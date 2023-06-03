package pl.majerowski.spacetask;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MongoTest {

    @Container
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", () ->   mongoDBContainer.getReplicaSetUrl());
    }

    @BeforeAll
    static void init() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void teardown() {
        mongoDBContainer.stop();
    }
}
