package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "authorities",
        uniqueConstraints = @UniqueConstraint(name = "username_auth_unique",
                columnNames = {"username", "authority"})
)
@AllArgsConstructor
@NoArgsConstructor
public class Authorities {
    @Id
    @GeneratedValue(generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "id_sequence", allocationSize = 1 )
    private Integer id;
    private String username;
    private String authority;

    public Authorities(String username, String authority){
        this.authority = authority;
        this.username = username;
    }
}
