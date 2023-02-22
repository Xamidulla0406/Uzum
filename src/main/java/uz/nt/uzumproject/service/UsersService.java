package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.UsersRepository;
import uz.nt.uzumproject.security.JwtService;
import uz.nt.uzumproject.service.mapper.UserMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = userMapper.toEntity(dto);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(userMapper.toDto(users))
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

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + usersDto.getId() + " is not found")
                    .code(-1)
                    .data(usersDto)
                    .build();
        }
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

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .success(true)
                    .message("OK")
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(userMapper.toDto(u))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    public List<ResponseDto<UsersDto>> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(u ->
                        ResponseDto.<UsersDto>builder()
                                .data(userMapper.toDto(u))
                                .success(true)
                                .message("OK")
                                .build()).collect(Collectors.toList());
    }

    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findFirstByEmail(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("User with this email " + username + " does not exist");

        return userMapper.toDto(user.get());
    }


    public ResponseDto<String> login(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
            return ResponseDto.<String>builder()
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .message(AppStatusMessages.NOT_FOUND)
                    .build();
        }

        return ResponseDto.<String>builder()
                .success(true)
                .message(AppStatusMessages.OK)
                .data(jwtService.generateToken(users))
                .build();
    }
}