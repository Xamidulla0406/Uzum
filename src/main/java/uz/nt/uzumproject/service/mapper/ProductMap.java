package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMap {
    Product toProduct(ProductDto productDto);
    ProductDto toProductDto(Product product);
}
