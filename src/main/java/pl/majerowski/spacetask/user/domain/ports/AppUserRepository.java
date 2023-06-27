package pl.majerowski.spacetask.user.domain.ports;

import pl.majerowski.spacetask.task.domain.model.Task;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.List;

public interface AppUserRepository {
    List<AppUser> findAllByEmail(String email);

    AppUser findById(String appUserId);

    void insert(AppUser appUser);

    void delete(String appUserId);

    void update(AppUser appUser);
}
