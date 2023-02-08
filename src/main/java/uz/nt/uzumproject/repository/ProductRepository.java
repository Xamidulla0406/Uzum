package uz.nt.uzumproject.repository;

import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.uzumproject.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByIdAndIsAvailable(Integer id, Boolean status);
}
