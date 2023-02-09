package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductDto dto) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setPrice(dto.getPrice());
        product.setAmount(dto.getAmount());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setIsAvailable(dto.getIsAvailable());

        return product;
    }

    public static ProductDto toDto(Product entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsAvailable(entity.getIsAvailable());

        return dto;
    }
}