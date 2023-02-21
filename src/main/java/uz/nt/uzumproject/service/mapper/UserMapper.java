package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UsersDto, Users> {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Override
    @Mapping(target = "isActive", expression = "java((short) 1)")
    @Mapping(target = "enabled",expression = "java(true)")
    @Mapping(target = "password",expression = "java(passwordEncoder.encode((dto.getPassword())))")
    @Mapping(target= "role",expression = "java(\"USER\")")
    abstract public Users toEntity(UsersDto dto);
    @Override
    abstract public UsersDto toDto(Users entity);
}
