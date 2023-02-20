package uz.nt.uzumproject.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.nt.uzumproject.security.UserRoles;
import uz.nt.uzumproject.service.validator.IsValidPhoneNumber;
import uz.nt.uzumproject.service.validator.ValidGender;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto implements UserDetails {
    private Integer id;
    @IsValidPhoneNumber
    private String phone;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    @ValidGender
    private String gender;
    private String password;
    private String birthDate;
    private String role = "USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRoles.valueOf(role)
                .getAuthorities().stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
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