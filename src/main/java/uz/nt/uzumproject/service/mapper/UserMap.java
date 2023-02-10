package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;

@Mapper(componentModel = "spring")
public interface UserMap {
    Users toUser(UsersDto usersDto);
    UsersDto toUserDto(Users users);
}
