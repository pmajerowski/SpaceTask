package pl.majerowski.spacetask;

import org.junit.ClassRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public class MongoContainerInitializerTest {

    @ClassRule
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:4.4.2").withEnv("MONGO_INITDB_DATABASE","spacetaskdb");
//            .withEnv("MONGO_INIT_ROOT_USERNAME","admin").withEnv("MONGO_INIT_ROOT_PASSWORD","admin");

    static {
        mongoDBContainer.start();
        System.out.println("AAAAAQQQQQQQQQXXXXX : " + mongoDBContainer.getFirstMappedPort());
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("mongodatasource.port", () -> mongoDBContainer.getFirstMappedPort());
    }
}