package pl.majerowski.spacetask.user.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class AppUser implements UserDetails {

    private String id;
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountActive;
    private boolean accountUnlocked;
    private boolean credentialsActive;
    private boolean enabled;


    public String getId() {
        return id;
    }
    @Override
    public String getUsername() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountUnlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsActive;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
