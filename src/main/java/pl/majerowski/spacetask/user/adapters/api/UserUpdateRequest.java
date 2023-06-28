package pl.majerowski.spacetask.user.adapters.api;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.Collection;

@Setter
public class UserUpdateRequest {
    private String id;
    private String username;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public AppUser asDomain() {
        return new AppUser(
                this.id,
                this.username,
                this.name,
                this.password,
                this.authorities,
                this.accountNonExpired,
                this.accountNonLocked,
                this.credentialsNonExpired,
                this.enabled
        );
    }
}
