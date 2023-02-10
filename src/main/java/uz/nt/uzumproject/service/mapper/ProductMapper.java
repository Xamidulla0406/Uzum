package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public abstract Product toEntity(ProductDto productDto);
    public abstract ProductDto toDto(Product product);
}
