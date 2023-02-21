package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.ProductService;

@Mapper(componentModel = "spring" , imports = {Product.class})
public abstract class UserMapper implements CommonMapper<UsersDto, Users>{

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ProductService productService;


    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "phoneNumber", source = "phone")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract Users toEntity(UsersDto dto);

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "product", expression = "java(productService.getAllProducts().getData())")
    @Mapping(target = "role", ignore = true)
    public abstract UsersDto toDto(Users entity);

}
