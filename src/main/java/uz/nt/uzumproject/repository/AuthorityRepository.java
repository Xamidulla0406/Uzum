package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Authorities;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authorities,Integer> {

    Optional<Authorities> getFirstByUsername(String  username);

    boolean existsAuthoritiesByUsernameAndAuthority(String username, String auth);
}
