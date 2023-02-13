package uz.nt.uzumproject.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.ProductService;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UsersDto, Users>{

//    @Autowired
//    protected ProductService productService;

    public abstract Users toEntity(UsersDto usersDto);
    public abstract UsersDto toDto(Users users);
}
