package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.nt.uzumproject.model.UserSession;

public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
