package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toEntity(UsersDto usersDto);

    UsersDto toDto(Users users);
}
