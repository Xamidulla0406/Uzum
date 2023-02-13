package uz.nt.uzumproject.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
@Mapper(componentModel = "spring")
public interface UserMapper extends CommonMapper<UsersDto,Users>{
    @Override
    @Mapping(target = "isActive", expression = "java((short) 1)")
    Users toEntity(UsersDto dto);
    @Override
    UsersDto toDto(Users entity);
}
