package uz.nt.uzumproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Authorities {
    @Id
    @SequenceGenerator(name = "seq_auth",sequenceName = "authSequence",initialValue = 1,allocationSize = 1)
    @GeneratedValue(generator = "seq_auth")
    private Integer id;
    private String username;
    private String authority;

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

}
