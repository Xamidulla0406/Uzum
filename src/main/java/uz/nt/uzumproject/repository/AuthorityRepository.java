package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Authorities;


@Repository
public interface AuthorityRepository extends JpaRepository<Authorities,Integer> {

//        boolean existsByUsernameAnAndAuthority(String username,String authority);
}
