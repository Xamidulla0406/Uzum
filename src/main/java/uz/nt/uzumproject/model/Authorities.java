package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "authorities",
        uniqueConstraints = @UniqueConstraint(name = "username_auth_unique",
                columnNames = {"username", "authority"})
)
public class Authorities {
    @Id
    @GeneratedValue(generator = "auth_id_seq")
    @SequenceGenerator(name = "auth_id_seq", sequenceName = "auth_id_seq", allocationSize = 1, initialValue = 3)
    private Integer id;

    private String username;
    private String authority;

    public Authorities(String username, String authority){
        this.username = username;
        this.authority = authority;
    }
}