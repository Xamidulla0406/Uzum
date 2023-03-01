package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query(name = "select * from product t where (t.category_id, t.price) in ")
}