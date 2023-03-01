package uz.nt.uzumproject.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product t where (t.categroy_id, t.price) in (select p.category_id, max(price) from product p" +
            "group by p.category_id)", nativeQuery = true)
    List<Product> getExpensiveProducts();

    @Query(name = "universalSearch")
    List<Product> universalSearch(@Param("id") Integer id,
                                  @Param("id") String name,
                                  @Param("amount") Integer amount,
                                  @Param("price")  Double price);
}
