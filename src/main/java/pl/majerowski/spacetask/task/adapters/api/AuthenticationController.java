package pl.majerowski.spacetask.task.adapters.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.majerowski.spacetask.config.JwtUtils;
import pl.majerowski.spacetask.task.adapters.dao.UserDao;
import pl.majerowski.spacetask.task.adapters.dto.AuthenticationRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDao.findUserByEmail(request.getEmail());

        if(user != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }

        return ResponseEntity.status(400).body("An error occurred");
    }
}
