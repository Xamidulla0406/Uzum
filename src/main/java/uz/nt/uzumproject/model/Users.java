package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class Users  {
    @Id
    @GeneratedValue(generator = "userIdSequence")
    @SequenceGenerator(name = "userIdSequence", sequenceName = "user_id_seq", allocationSize = 1)

    private Integer id;
    private String phoneNumber;
    private String firstName;
    private String password;
    private String lastName;
    private String fathersName;
    private String email;
    private String gender;
    private Date birthDate;
    private short isActive;
    private String role= "USER";


}