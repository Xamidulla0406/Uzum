package uz.nt.uzumproject.repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product t where (t.category_id, t.price) in " +
            "(select p.category_id, max(p.price) from product p group by p.category_id)",
            nativeQuery = true)
    List<Product> getExpensiveProducts();

    @Query(value = "select t from Product t where (t.category.id, t.price) in" +
            "(select p.category.id, max(p.price) from Product p group by p.category.id)")
    List<Product> getExpensiveProducts2();

    @Query(name = "findProductById")
    List<Product> findProducts(@Param("id")Integer id,
                                      @Param("name") String name,
                                      @Param("amount") Integer amount,
                                      @Param("price") Integer price);

    @Query(value = "select * from product p where p.amount <= :amount order by p.id", nativeQuery = true)
    List<Product> findAllByAmountLessThanEqual(@Param("amount") Integer amount);
}
