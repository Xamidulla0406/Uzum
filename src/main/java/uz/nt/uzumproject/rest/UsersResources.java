package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources{
    private final UsersService usersService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @GetMapping("login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }

    @GetMapping("users-list")
//    @PreAuthorize("hasRole('USER')")
    private List<ResponseDto<UsersDto>> getUserById(){
        return usersService.getAllUsers();
    }




}