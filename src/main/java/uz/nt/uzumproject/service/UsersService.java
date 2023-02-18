package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.service.mapper.UsersMapper;
import uz.nt.uzumproject.service.mapper.UsersMapperManual;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = usersMapper.toEntity(dto);
        users.setIsActive((short) 1);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(usersMapper.toDto(users))
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

        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());
        Users user = userOptional.get();

        if (userOptional.isEmpty() || user.getIsActive() == 0 ){
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + usersDto.getId() + " is not found")
                    .code(-1)
                    .data(usersDto)
                    .build();
        }
        if (usersDto.getGender() != null){
            user.setGender(usersDto.getGender());
        }
        if (usersDto.getEmail() != null){
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getLastName() != null){
            user.setLastName(usersDto.getLastName());
        }
        //...
        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(UsersMapperManual.toDto(user))
                    .success(true)
                    .message("OK")
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(UsersMapperManual.toDto(user))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(UsersMapperManual.toDto(u))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> optionalUser = usersRepository.findById(id);

        Users users = optionalUser.get();

        if(optionalUser.isEmpty() || users.getIsActive() == 0){
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + id + " is not found")
                    .code(-1)
                    .build();
        }

        users.setIsActive((short) 0);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .message("User with ID " + id + " is deleted")
                .code(0)
                .data(UsersMapperManual.toDto(users))
                .build();
    }

    public List<ResponseDto<UsersDto>> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(u ->
                ResponseDto.<UsersDto>builder()
                        .data(UsersMapperManual.toDto(u))
                        .success(true)
                        .message("OK")
                        .build()).collect(Collectors.toList());
    }
}