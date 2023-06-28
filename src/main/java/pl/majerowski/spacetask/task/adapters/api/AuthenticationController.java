package pl.majerowski.spacetask.task.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.majerowski.spacetask.task.adapters.dto.AuthenticationRequest;
import pl.majerowski.spacetask.task.auth.JwtUtils;
import pl.majerowski.spacetask.user.domain.model.AppUser;
import pl.majerowski.spacetask.user.domain.ports.AppUserService;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationManager authenticationManager, AppUserService appUserService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final AppUser user = appUserService.findByEmail(request.getEmail());

        return ResponseEntity.ok(new AuthenticationResponse(jwtUtils.generateToken(user)));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthenticationResponse {
        private String token;
    }
}
