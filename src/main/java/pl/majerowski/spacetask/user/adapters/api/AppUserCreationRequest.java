package pl.majerowski.spacetask.user.adapters.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserCreationRequest {
    private String email;
    private String name;
    private String password;

    public AppUser asDomain() {
        return new AppUser(
                null,
                this.email,
                this.name,
                this.password,
                Collections.emptyList(),
                true,
                true,
                true,
                true
        );
    }
}
