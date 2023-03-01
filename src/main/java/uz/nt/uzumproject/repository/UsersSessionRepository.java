package uz.nt.uzumproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.UsersSession;

@Repository
public interface UsersSessionRepository extends CrudRepository<UsersSession, String> {
}
