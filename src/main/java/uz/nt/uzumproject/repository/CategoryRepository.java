package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findAllByParentId(Integer id);

    Collection<Object> findFirstById(Integer parentId);

}
