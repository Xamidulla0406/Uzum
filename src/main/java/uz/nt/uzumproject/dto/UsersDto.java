package uz.nt.uzumproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.nt.uzumproject.security.UserRoles;
import uz.nt.uzumproject.service.validator.IsValidPhoneNumber;
import uz.nt.uzumproject.service.validator.ValidGender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password", "username", "authorities",
        "role", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"},allowSetters = true)
public class UsersDto implements UserDetails {
    private Integer id;
    @IsValidPhoneNumber
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    //@ValidGender
    private String gender;
    private String password;
    private boolean enabled;
    private Date birthDate;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserRoles.valueOf(role)
                .getAuthorities().stream()
                    .map(a->new SimpleGrantedAuthority(a))
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
    public boolean isEnabled(){
        return true;
    }
}