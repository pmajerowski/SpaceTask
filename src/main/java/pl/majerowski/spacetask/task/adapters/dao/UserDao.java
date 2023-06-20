package pl.majerowski.spacetask.task.adapters.dao;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {

    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User(
                    "admin@admin.com",
                    "password",
                    Collections.emptyList()
            ),
            new User(
                    "user@user.com",
                    "password",
                    Collections.emptyList()
            )
    );

    public UserDetails findUserByEmail(String email) {
        return APPLICATION_USERS.stream()
                .filter(u -> u.getUsername().equals(email))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
