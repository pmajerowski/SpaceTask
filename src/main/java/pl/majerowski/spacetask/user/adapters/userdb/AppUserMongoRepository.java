package pl.majerowski.spacetask.user.adapters.userdb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.List;

public interface AppUserMongoRepository extends MongoRepository<AppUserDocument, String> {
    AppUserDocument findByEmail(String email);
}
