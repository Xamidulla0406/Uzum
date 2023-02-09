package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.service.mapper.UsersMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = UsersMapper.toEntity(dto);
        users.setIsActive((short) 1);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(UsersMapper.toDto(users))
                .message("OK")
                .build();
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message("UserID is null")
                    .code(-2)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findByIdAndIsActive(usersDto.getId(), (short) 1);

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + usersDto.getId() + " is not found")
                    .code(-1)
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
                    .data(UsersMapper.toDto(user))
                    .success(true)
                    .message("OK")
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(UsersMapper.toDto(user))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumberAndIsActive(phoneNumber, (short) 1)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(UsersMapper.toDto(u))
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
                    .data(UsersMapper.toDto(delUser))
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
                .data(usersRepository.findAllByIsActive(1).stream().map(u->UsersMapper.toDto(u)).collect(Collectors.toList()))
                .build();
    }
}