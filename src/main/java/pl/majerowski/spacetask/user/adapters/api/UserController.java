package pl.majerowski.spacetask.user.adapters.api;

import org.springframework.web.bind.annotation.*;
import pl.majerowski.spacetask.user.domain.model.AppUser;
import pl.majerowski.spacetask.user.domain.ports.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping(produces = "application/json")
    public AppUser getUserByEmail(@RequestParam String userEmail) {
        return appUserService.findByEmail(userEmail);
    }

    @GetMapping(produces = "application/json")
    public AppUser getUserById(@RequestParam String userId) {
        return appUserService.findByEmail(userId);
    }

    @PostMapping(consumes = "application/json")
    public void postUser(@RequestBody AppUserCreationRequest appUserCreationRequest) {

    }
}
