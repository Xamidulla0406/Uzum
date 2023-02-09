package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductDto dto){
        Product product = new Product();

        product.setId(dto.getId());
        product.setAmount(dto.getAmount());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setIsAvailable(dto.getIsAvailable());

        return product;
    }

    public static ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();

        dto.setAmount(dto.getAmount());
        dto.setId(dto.getId());
        dto.setDescription(product.getDescription());
        dto.setName(dto.getName());
        dto.setPrice(product.getPrice());
        dto.setIsAvailable(product.getIsAvailable());

        return dto;
    }
}
