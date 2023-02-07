package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

}