package uz.nt.uzumproject.rest;

import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

@RestController
@RequestMapping("/user")
public record UsersResources(UsersService usersService) {
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto) {
        return usersService.updateUser(usersDto);
    }

    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber) {
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("login")
    public ResponseDto<String> loginUser(@RequestBody LoginDto dto) {
        return usersService.loginUser(dto);
    }
}