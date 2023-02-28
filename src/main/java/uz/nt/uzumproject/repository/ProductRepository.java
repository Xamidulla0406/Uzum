package uz.nt.uzumproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from Product",nativeQuery = true)
    List<Product> productNative();

    @Query(value = "select product from Product product")
    List<Product> productHQL();

    @Query(name = "ProductGet")
    List<Product> productNamed();


}
