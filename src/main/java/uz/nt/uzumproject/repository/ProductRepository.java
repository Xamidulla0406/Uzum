package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product p where (p.category_id, p.price) in (select p1.category_id, max(p1.price) from product p1 " +
            "group by p1.category_id)", nativeQuery = true)
    List<Product> getExpensiveProducts();

    @Query(value = "select p from Product p where (p.category.id, p.price) in (select p1.category.id, max(p1.price) from Product p1 " +
            "group by p1.category.id)")
    List<Product> getExpensiveProducts2();

    @Query(name = "findProductById")
    List<Product> findProductById(@Param("id") Integer id,
                                  @Param("name") String name,
                                  @Param("amount") Integer amount,
                                  @Param("price") Integer price);
}
