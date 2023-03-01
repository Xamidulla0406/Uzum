package uz.nt.uzumproject.repository;

import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumberAndIsActive(String phoneNumber,Short isActive);
    Optional<Users> findByIdAndIsActive(Integer id,Short isActive);
    List<Users> findAllByIsActive(Integer status);

    Optional<Users> findFirstByEmail(String email);
}