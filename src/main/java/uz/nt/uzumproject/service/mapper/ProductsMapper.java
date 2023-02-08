package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductsDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;

public class ProductsMapper {
    public static Product toEntity(ProductsDto dto) {
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

    public static ProductsDto toDto(Product entity) {
        ProductsDto dto = new ProductsDto();

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
