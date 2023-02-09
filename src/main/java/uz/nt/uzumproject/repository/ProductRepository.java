package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
