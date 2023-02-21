package uz.nt.uzumproject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Authorities;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authorities,Integer> {
    List<Authorities> findAllByUsername(String username);
    boolean existsByUsernameAndAuthority(String username, String auth);
}
