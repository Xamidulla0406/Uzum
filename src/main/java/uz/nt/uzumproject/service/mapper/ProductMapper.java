package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDTO(Product product);

}
