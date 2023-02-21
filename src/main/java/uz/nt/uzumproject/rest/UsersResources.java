package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.secutiry.JwtService;
import uz.nt.uzumproject.service.UsersService;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }
//    @PreAuthorize("hasRole('Admin')")

    @GetMapping
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/user") ResponseDto<?> getById(@RequestBody LoginDto loginDto){
        UsersDto usersDto = usersService.loadUserByUsername(loginDto.getUsername());
        if(!passwordEncoder.matches(loginDto.getPassword(), usersDto.getPassword()))
            return ResponseDto.<Void>builder()
                    .message("Password is not correct")
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .build();
        return ResponseDto.<String>builder()
                .success(true)
                .message(AppStatusMessages.OK)
                .data(jwtService.jwts(usersDto))
                .build();
    }
}