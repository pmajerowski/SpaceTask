package pl.majerowski.spacetask.user.adapters.api;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.Collection;

@Setter
public class UserUpdateRequest {
    private String id;
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountActive;
    private boolean accountUnlocked;
    private boolean credentialsActive;
    private boolean enabled;

    public AppUser asDomain() {
        return new AppUser(
                this.id,
                this.email,
                this.name,
                this.password,
                this.authorities,
                this.accountActive,
                this.accountUnlocked,
                this.credentialsActive,
                this.enabled
        );
    }
}
