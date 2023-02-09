package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}