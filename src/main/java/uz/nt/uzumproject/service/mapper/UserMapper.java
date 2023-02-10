package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;

public interface UserMapper {
    Users toEntity(UsersDto dto);
    UsersDto toDto(Users users);
}
