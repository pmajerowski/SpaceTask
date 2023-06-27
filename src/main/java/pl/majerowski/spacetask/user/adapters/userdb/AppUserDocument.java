package pl.majerowski.spacetask.user.adapters.userdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import pl.majerowski.spacetask.user.domain.model.AppUser;

import java.util.Collection;

@AllArgsConstructor
@Getter
@Document(collection = "users")
public class AppUserDocument {

    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountActive;
    private boolean accountUnlocked;
    private boolean credentialsActive;
    private boolean enabled;

    public static AppUser asDomain(AppUserDocument appUserDocument) {
        return new AppUser(
                appUserDocument.id,
                appUserDocument.email,
                appUserDocument.name,
                appUserDocument.password,
                appUserDocument.authorities,
                appUserDocument.accountActive,
                appUserDocument.accountUnlocked,
                appUserDocument.credentialsActive,
                appUserDocument.enabled
        );
    }

    public static AppUserDocument asDocument(AppUser appUser) {
        return new AppUserDocument(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getName(),
                appUser.getPassword(),
                appUser.getAuthorities(),
                appUser.isAccountNonExpired(),
                appUser.isAccountNonLocked(),
                appUser.isCredentialsNonExpired(),
                appUser.isEnabled()
        );
    }
}
