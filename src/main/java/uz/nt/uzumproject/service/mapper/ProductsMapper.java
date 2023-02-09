package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public class ProductsMapper {
    public static Product toEntity(ProductDto dto) {
        Product entity = new Product();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAmount(dto.getAmount());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setIsAvailable(dto.getIsAvailable());

        return entity;
    }

    public static ProductDto toDto(Product entity) {
        ProductDto dto = new ProductDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setImageUrl(entity.getImageUrl());

        return dto;
    }
}
