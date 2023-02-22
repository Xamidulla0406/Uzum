package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected CategoryMapper categoryMapper;


    public abstract Product toEntity(ProductDto productDto);
    @Mapping(target = "category", expression = "java(categoryMapper.toDto(product.getCategory()))")
    public abstract ProductDto toDto(Product product);

}
