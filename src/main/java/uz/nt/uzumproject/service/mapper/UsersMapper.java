package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;

public class UsersMapper {
    public static Users toEntity(UsersDto dto) {
        Users entity = new Users();

        entity.setId(dto.getId());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setEmail(dto.getEmail());
        entity.setGender(dto.getGender());
<<<<<<< HEAD
        entity.setBirthDate(dto.getBirthDate());
=======
        entity.setBirthDate(dto.getBirhtDate());
>>>>>>> origin/develop

        return entity;
    }

    public static UsersDto toDto(Users entity) {
        UsersDto dto = new UsersDto();

        dto.setId(entity.getId());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setEmail(entity.getEmail());
        dto.setGender(entity.getGender());
<<<<<<< HEAD
        dto.setBirthDate(entity.getBirthDate());
=======
        dto.setBirhtDate(entity.getBirthDate());
>>>>>>> origin/develop

        return dto;
    }

}