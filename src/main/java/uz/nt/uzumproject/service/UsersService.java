package uz.nt.uzumproject.service;

import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.service.mapper.UsersMapper;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


=======
import java.util.Optional;

>>>>>>> origin/develop
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
                .code(0)
                .data(UsersMapper.toDto(users))
                .message("OK")
                .build();
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
<<<<<<< HEAD
        if (usersDto.getId() == null) {
            return ResponseDto.<UsersDto>builder()
                    .message("User id is null")
=======
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message("UserID is null")
>>>>>>> origin/develop
                    .code(-2)
                    .data(usersDto)
                    .build();
        }

<<<<<<< HEAD
        Optional<Users> optionalUsers = usersRepository.findById(usersDto.getId());


        if (optionalUsers.isEmpty() || optionalUsers.get().getIsActive() == 0) {
            return ResponseDto.<UsersDto>builder()
                    .message("User is not found with " + usersDto.getId() + " id")
=======
        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + usersDto.getId() + " is not found")
>>>>>>> origin/develop
                    .code(-1)
                    .data(usersDto)
                    .build();
        }
<<<<<<< HEAD
        Users user = optionalUsers.get();

        if (usersDto.getFirstName() != null) {
            user.setFirstName(usersDto.getFirstName());
        }
        if (usersDto.getLastName() != null) {
            user.setLastName(usersDto.getLastName());
        }
        if (usersDto.getMiddleName() != null) {
            user.setMiddleName(usersDto.getMiddleName());
        }
        if (usersDto.getPhoneNumber() != null) {
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }
        if (usersDto.getEmail() != null) {
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getBirthDate() != null) {
            user.setBirthDate(usersDto.getBirthDate());
        }
        if (usersDto.getGender() != null) {
            user.setGender(user.getGender());
        }
        try {
            usersRepository.save(user);
=======
        Users user = userOptional.get();
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

>>>>>>> origin/develop
            return ResponseDto.<UsersDto>builder()
                    .data(UsersMapper.toDto(user))
                    .success(true)
                    .message("OK")
                    .build();
<<<<<<< HEAD

        } catch (Exception e) {
=======
        }catch (Exception e){
>>>>>>> origin/develop
            return ResponseDto.<UsersDto>builder()
                    .data(UsersMapper.toDto(user))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
<<<<<<< HEAD

    }

    public ResponseDto<UsersDto> getPhoneNumber(String phoneNumber) {


        return usersRepository.findFirstByPhoneNumberAndIsActive(phoneNumber, (short) 1)
                .map(n -> ResponseDto.<UsersDto>builder()
                        .data(UsersMapper.toDto(n))
                        .message("OK")
                        .success(true)
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User is not found with " + phoneNumber + " phone number")
                        .code(-1)
                        .build());
    }

    public ResponseDto<UsersDto> deleteUser(Integer userId) {
        Optional<Users> optionalUsers = usersRepository.findById(userId);
        Users user = optionalUsers.get();

        if (optionalUsers.get().getIsActive() == 1) {
            user.setIsActive((short) 0);
            usersRepository.save(user);
            return ResponseDto.<UsersDto>builder()
                    .message("Deleted")
                    .data(UsersMapper.toDto(optionalUsers.get()))
                    .code(0)
                    .build();
        }

        return (ResponseDto.<UsersDto>builder()
                .message("User is not found with " + userId + " id")
                .code(-1)
                .build());

    }

    public List<Users> getAll() {
        return usersRepository.findByIsActive(1);
    }
=======
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
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
>>>>>>> origin/develop
}