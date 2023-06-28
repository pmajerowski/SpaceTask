package pl.majerowski.spacetask.task.adapters.taskdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMongoRepository extends MongoRepository<TaskDocument, String> {

    List<TaskDocument> findAllByEmail(String email);

}
