package uz.nt.uzumproject.rest;

<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
>>>>>>> origin/develop
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.service.UsersService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;

    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

<<<<<<< HEAD
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
=======
    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
>>>>>>> origin/develop
    }
}