package pl.majerowski.spacetask.user.adapters.userdb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserMongoRepository extends MongoRepository<AppUserDocument, String> {
}
