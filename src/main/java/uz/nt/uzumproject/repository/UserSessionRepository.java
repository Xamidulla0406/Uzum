package uz.nt.uzumproject.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.UserSession;


@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {


}
