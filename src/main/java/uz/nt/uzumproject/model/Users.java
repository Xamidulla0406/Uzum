package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(generator = "userIdSequence")
    @SequenceGenerator(name = "userIdSequence", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private Date birthDate;
    private String password;
    private Boolean enabled;
    @Column(columnDefinition = "text default 'USER'")
    private String role = "USER";
}