package uz.nt.uzumproject.service.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.UsersService;

//@RequiredArgsConstructor
@Mapper(componentModel = "spring")
public abstract class UsersMapper {

    @Autowired
    protected  UsersService usersService;

    @Mapping(target = "fathersName", source = "middleName")
    public abstract Users toEntity(UsersDto dto);



    @Mapping(target = "middleName", source = "fathersName")
    public abstract UsersDto toDto(Users users);

}
