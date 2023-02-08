package uz.nt.uzumproject.rest;

import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.UsersService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;

    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @GetMapping()
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber) {
        return usersService.getPhoneNumber(phoneNumber);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUsers(@RequestBody UsersDto usersDto) {
        return usersService.updateUser(usersDto);
    }

    @DeleteMapping
    public ResponseDto<UsersDto> deleteUserById(@RequestParam Integer userId) {
        return usersService.deleteUser(userId);
    }

    @GetMapping("/getAll")
    public List<Users> getAllUsers() {
        return usersService.getAll();
    }
}