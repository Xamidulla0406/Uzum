package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.ProductService;

@Mapper(componentModel = "spring" , imports = {Product.class})
public abstract class UserMapper implements CommonMapper<UsersDto, Users>{

    @Autowired
    protected ProductService productService;

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "phoneNumber", source = "phone")
    public abstract Users toEntity(UsersDto dto);

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "product", expression = "java(productService.getAllProducts().getData())")
    public abstract UsersDto toDto(Users entity);

}
