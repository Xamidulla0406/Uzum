package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.model.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public List<Category> findAllByParentId(Integer id);
}
