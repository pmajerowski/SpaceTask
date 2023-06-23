package pl.majerowski.spacetask.task.adapters.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {

    private Map<String, User> getUsers() {
        return Map.of(
                "admin@admin.com", new User(
                        "admin@admin.com",
                        "$2a$10$9vN6jmWOw9BvuJVC8YgZEuMKLlrcYIkJBMZfDg/xhLUQC5.xlz1Ry",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                ),
                "user@user.com", new User(
                        "user@user.com",
                        "$2a$10$9vN6jmWOw9BvuJVC8YgZEuMKLlrcYIkJBMZfDg/xhLUQC5.xlz1Ry",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
    }

    public UserDetails findUserByEmail(String email) {
        return Optional.ofNullable(getUsers().get(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
