package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.model.Category;

import java.util.Collection;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Collection<? extends CategoryDto> findAllByParentId(Integer parentId);


}
