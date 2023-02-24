package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
