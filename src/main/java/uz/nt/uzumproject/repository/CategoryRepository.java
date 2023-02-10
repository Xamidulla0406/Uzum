package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Category;

import java.util.Collection;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Collection<? extends Category> findAllByParentId(Integer id);
}
