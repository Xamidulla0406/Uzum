package uz.nt.uzumproject.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.ProductService;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UsersDto, Users>{

    @Autowired
//    protected ProductService productService;
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(usersDto.getPassword()))")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", ignore = true)
    public abstract Users toEntity(UsersDto usersDto);
    public abstract UsersDto toDto(Users users);
}
