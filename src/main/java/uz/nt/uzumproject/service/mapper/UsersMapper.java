package uz.nt.uzumproject.service.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.UsersService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Mapper(componentModel = "spring")
public abstract class UsersMapper {

    @Autowired
    protected  UsersService usersService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "fathersName", source = "middleName")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract Users toEntity(UsersDto dto);

    @Mapping(target = "middleName", source = "fathersName")
    public abstract UsersDto toDto(Users users);





}
