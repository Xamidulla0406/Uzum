package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product t where (t.category_id, t.price) in (select p.category_id, max(p.price) from product p group by p.category_id)",
    nativeQuery = true)
    List<Product> getExpensiveProducts();
    @Query(name = "findById")
    Optional<Product> findById(@Param("id") Integer id);
    List<Product> findAllByAmountLessThanEqual(Integer amount);
}
