package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "authority",
        uniqueConstraints = @UniqueConstraint(name = "username_auth_unique",
                columnNames = {"username","authority"})
)
public class Authorities {
    @Id
    @GeneratedValue(generator = "auth_id_seq")
    @SequenceGenerator(name = "auth_id_seq", sequenceName = "auth_id_seq", allocationSize = 1)
    private Integer id;
    private String username;
    private String authority;

    public Authorities(String username, String authority){
        this.authority=authority;
        this.username=username;
    }

    public Authorities() {

    }
}
