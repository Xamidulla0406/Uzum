package uz.nt.uzumproject.repository;

import org.springframework.data.repository.CrudRepository;
import uz.nt.uzumproject.model.UsersSession;

public interface UserSessionRepository extends CrudRepository<UsersSession,String> {
}
