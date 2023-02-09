package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public class ProductMapper {
    public static ProductDto toDTO(Product entity){
        ProductDto dto=new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setIsAvailable(entity.getIsAvailable());
        return dto;
    }
    public static Product toEntity(ProductDto dto){
        Product entity=new Product();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setAmount(dto.getAmount());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setIsAvailable(dto.getIsAvailable());
        return entity;
    }
}
