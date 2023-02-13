package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
