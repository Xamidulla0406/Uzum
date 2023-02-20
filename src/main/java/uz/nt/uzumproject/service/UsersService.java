package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = userMapper.toEntity(dto);

        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(userMapper.toDto(users))
                .message(OK)
                .code(OK_CODE)
                .build();
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findByIdAndIsActive(usersDto.getId(), (short) 1);

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
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
            user.setBirthDate(usersDto.getBirthDate());
        }
        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(OK_CODE)
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(1)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumberAndIsActive(phoneNumber, (short) 1)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(userMapper.toDto(u))
                        .success(true)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message(NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build());
    }

    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> user=usersRepository.findByIdAndIsActive(id,(short)1);
        if(user.isEmpty()) {
            return (ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build());
        }
        Users delUser= user.get();
        delUser.setIsActive((short) 0);
        try {
            usersRepository.save(delUser);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(userMapper.toDto(delUser))
                    .build();

        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(OK_CODE)
                    .build();
        }
    }

    public ResponseDto<List<UsersDto>> getAllUsers() {
        return ResponseDto.<List<UsersDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(usersRepository
                        .findAllByIsActive(1)
                        .stream()
                        .map(u-> userMapper.toDto(u))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByEmail(username);
        if(users.isEmpty()) throw new UsernameNotFoundException("user is not found");

        return userMapper.toDto(users.get());
    }
}