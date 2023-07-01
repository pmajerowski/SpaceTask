package pl.majerowski.spacetask.user.adapters.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.majerowski.spacetask.user.domain.model.AppUser;
import pl.majerowski.spacetask.user.domain.ports.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(AppUserService appUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserService = appUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(produces = "application/json")
    public AppUser getUserByEmail(@RequestParam String userEmail) {
        return appUserService.findByEmail(userEmail);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<AppUser> getAllUsers() {
        return appUserService.findAllUsers();
    }

    @PostMapping(consumes = "application/json")
    public void postUser(@RequestBody AppUserCreationRequest appUserCreationRequest) {
        appUserCreationRequest.setPassword(
               bCryptPasswordEncoder.encode(appUserCreationRequest.getPassword())
        );
        AppUser appUser = appUserCreationRequest.asDomain();
        appUserService.insert(appUser);
    }

    @PutMapping(consumes = "application/json")
    public void updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        userUpdateRequest.setPassword(
                bCryptPasswordEncoder.encode(userUpdateRequest.getPassword())
        );
        AppUser appUser = userUpdateRequest.asDomain();
        appUserService.update(appUser);
    }

    @DeleteMapping
    public void deleteUserById(@RequestParam String userId) {
        appUserService.delete(userId);
    }
}
