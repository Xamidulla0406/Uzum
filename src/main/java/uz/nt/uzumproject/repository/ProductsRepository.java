package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;
@Repository
public interface ProductsRepository extends JpaRepository<Product,Integer> {
}
