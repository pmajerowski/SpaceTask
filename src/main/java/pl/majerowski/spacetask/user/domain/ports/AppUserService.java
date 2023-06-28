package pl.majerowski.spacetask.user.domain.ports;

import org.springframework.stereotype.Service;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.List;

@Service
public final class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public AppUser findById(String appUserId) {
        return appUserRepository.findById(appUserId);
    }

    public void insert(AppUser appUser) {
        appUserRepository.insert(appUser);
    }

    public void  delete(String appUserId) {
        appUserRepository.delete(appUserId);
    }

    public void update(AppUser appUser) {
        appUserRepository.update(appUser);
    }

    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }
}
