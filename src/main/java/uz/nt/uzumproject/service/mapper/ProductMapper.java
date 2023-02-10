package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public interface ProductMapper {
    Product toEntity(ProductDto dto);
    ProductDto toDto(Product product);
}
