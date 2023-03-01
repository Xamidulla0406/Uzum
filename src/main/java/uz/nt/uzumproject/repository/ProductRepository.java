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
    @Query(value = "select * from product p where (p.price, p.category_id) in (select max(p2.price), p2.category_id from product p2 group by p2.category_id)",
    nativeQuery = true)
    List<Product> getExpensiveProducts();

    @Query(value = "select p from Product p where (p.price, p.category.id) in (select max(p2.price), p2.category.id from Product p2 group by p2.category.id)")
    List<Product> getExpensiveProducts2();

    @Query(name = "findProductById")
    List<Product> findProductById(@Param("id") Integer id,
                                      @Param("name") String name,
                                      @Param("amount") Integer amount,
                                      @Param("price") Integer price);

    List<Product> findAllByAmountLessThanEqual(Integer amount);
}
