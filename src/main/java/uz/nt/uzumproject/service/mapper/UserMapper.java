package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.ProductService;

@Mapper(componentModel = "Spring")
public abstract class UserMapper {

    @Autowired
    protected ProductService productService;

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    public abstract Users toEntity(UsersDto usersDto);

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "productDtos", expression = "java(productService.get().getData())")
    public abstract UsersDto toDto(Users users);
}
