package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

@Mapper(componentModel = "Spring")
public interface ProductMapper extends CommonMapper<ProductDto,Product>{

}
