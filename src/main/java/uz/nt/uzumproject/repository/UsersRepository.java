package uz.nt.uzumproject.repository;

import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Users;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> origin/develop
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
<<<<<<< HEAD
    Optional<Users> findFirstByPhoneNumberAndIsActive(String phoneNumber, Short isActive);

    List<Users> findByIsActive(int status);

=======
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
>>>>>>> origin/develop
}