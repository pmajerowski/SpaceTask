package pl.majerowski.spacetask.user.domain.ports;

import org.springframework.stereotype.Service;
import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.List;

@Service
public final class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> findAllByEmail(String email) {
        return appUserRepository.findAllByEmail(email);
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
}
