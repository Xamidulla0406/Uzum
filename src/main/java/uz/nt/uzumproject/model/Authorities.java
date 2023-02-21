package uz.nt.uzumproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(name = "authorities",
//        uniqueConstraints = @UniqueConstraint(name = "username_auth_unique", columnNames = {"username,authority"}))
public class Authorities {
    @GeneratedValue(generator = "authority_id")
    @SequenceGenerator(sequenceName = "authority_id",name = "authority_id_seq", allocationSize = 1)
    @Id
    Integer id;
    String username;
    String authority;
}
