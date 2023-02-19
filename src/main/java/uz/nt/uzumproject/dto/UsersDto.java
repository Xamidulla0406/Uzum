package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.nt.uzumproject.security.UserRoles;

import java.util.Collection;
import java.util.Date;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto  implements UserDetails {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private String password;
    private Date birthDate;
    private String role="USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRoles.valueOf(role).getAuthorities().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}