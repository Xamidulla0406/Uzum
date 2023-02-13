package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

@Mapper (componentModel = "spring")
public interface ProductMapper extends CommonMapper<ProductDto, Product>{
}
