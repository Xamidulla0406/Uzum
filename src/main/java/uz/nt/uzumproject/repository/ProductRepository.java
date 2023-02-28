package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product t where (t.category_id , t.price) in (select p.category_id,max(p.price) from product p group by p.category_id)",nativeQuery = true)
    List<Product> getExpensiveProductsWithNativeQuery();

    @Query(value = "select t from Product t where (t.category.id, t.price) in (select p.category.id,max(p.price) from Product p group by p.category.id)")
    List<Product> getExpensiveProductsWithHQL();

    @Query(name = "universalSearch")
    List<Product> universalSearch(@Param("id") Integer id,
                                  @Param("name") String name,
                                  @Param("price") Integer price,
                                  @Param("amount") Integer amount);
}
