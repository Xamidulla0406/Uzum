package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductsDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;

public class ProductMapper {
    public static Product toEntity(ProductsDto dto) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setPrice(dto.getPrice());
        product.setAmount(dto.getAmount());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setIsAvailable(dto.getIsAvailable());

        return product;
    }

    public static ProductsDto toDto(Product entity) {
        ProductsDto dto = new ProductsDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsAvailable(entity.getIsAvailable());

        return dto;
    }
}