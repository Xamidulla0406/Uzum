package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findFirstByIdAndIsAvailable(Integer id, Boolean isAvailable);
}
