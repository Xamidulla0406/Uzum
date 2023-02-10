package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.service.ProductService;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {


    @Mapping(target = "isAvailable", expression = "java(true)")
    public abstract Product toEntity(ProductDto dto);


    public abstract ProductDto toProduct(Product product);
}
