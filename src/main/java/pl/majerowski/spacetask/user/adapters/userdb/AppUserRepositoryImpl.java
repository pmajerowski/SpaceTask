package pl.majerowski.spacetask.user.adapters.userdb;

import org.springframework.stereotype.Repository;
import pl.majerowski.spacetask.user.domain.model.AppUser;
import pl.majerowski.spacetask.user.domain.ports.AppUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {

    private final AppUserMongoRepository appUserMongoRepository;

    public AppUserRepositoryImpl(AppUserMongoRepository appUserMongoRepository) {
        this.appUserMongoRepository = appUserMongoRepository;
    }

    @Override
    public AppUser findByEmail(String email) {
        return AppUserDocument.asDomain(appUserMongoRepository.findByEmail(email));

    }

    @Override
    public AppUser findById(String appUserId) {
        return appUserMongoRepository.findById(appUserId).map(AppUserDocument::asDomain).orElseThrow();
    }

    @Override
    public void insert(AppUser appUser) {
        appUserMongoRepository.insert(AppUserDocument.asDocument(appUser));
    }

    @Override
    public void delete(String appUserId) {
        appUserMongoRepository.deleteById(appUserId);
    }

    @Override
    public void update(AppUser appUser) {
        appUserMongoRepository.save(AppUserDocument.asDocument(appUser));
    }

    @Override
    public List<AppUser> findAll() {
        return appUserMongoRepository.findAll().stream().map(AppUserDocument::asDomain).collect(Collectors.toList());
    }
}
