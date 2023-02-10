package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.service.mapper.UserMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UserMapper mapper;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = mapper.toEntity(dto);
        users.setIsActive((short) 1);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(mapper.toDto(users))
                .message("OK")
                .build();
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message(AppStatusMessages.NULL_VALUE)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findByIdAndIsActive(usersDto.getId(), (short) 1);

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_CODE)
                    .data(usersDto)
                    .build();
        }
        Users user = userOptional.get();
        if (usersDto.getFirstName() != null) {
            user.setFirstName(usersDto.getFirstName());
        }
        if (usersDto.getLastName() != null) {
            user.setLastName(usersDto.getLastName());
        }
        if (usersDto.getMiddleName() != null) {
            user.setMiddleName(usersDto.getMiddleName());
        }
        if (usersDto.getEmail() != null) {
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getGender() != null) {
            user.setGender(usersDto.getGender());
        }

        if (usersDto.getPhoneNumber() != null) {
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }
        if (usersDto.getBirthDate() != null) {
            user.setBirthDate(Date.valueOf(usersDto.getBirthDate()));
        }
        try {
            usersRepository.save(user);
            return ResponseDto.<UsersDto>builder()
                    .data(mapper.toDto(user))
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .message(AppStatusMessages.OK)
                    .build();
        } catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(mapper.toDto(user))
                    .code(AppStatusCodes.UNEXPECTED_ERROR_CODE)
                    .message(AppStatusMessages.UNEXPECTED_ERROR + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumberAndIsActive(phoneNumber, (short) 1)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(mapper.toDto(u))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> user=usersRepository.findByIdAndIsActive(id,(short)1);
        if(user.isEmpty()) {
            return (ResponseDto.<UsersDto>builder()
                    .message("User with ID=" + id + " not found")
                    .code(-1)
                    .build());
        }
        Users delUser= user.get();
        delUser.setIsActive((short) 0);
        try {
            usersRepository.save(delUser);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message("OK")
                    .data(mapper.toDto(delUser))
                    .build();

        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(1)
                    .build();
        }
    }

    public ResponseDto<List<UsersDto>> getAllUsers() {
        return ResponseDto.<List<UsersDto>>builder()
                .code(0)
                .message("OK")
                .success(true)
                .data(usersRepository.findAllByIsActive(1).stream().map(u-> mapper.toDto(u)).collect(Collectors.toList()))
                .build();
    }
}