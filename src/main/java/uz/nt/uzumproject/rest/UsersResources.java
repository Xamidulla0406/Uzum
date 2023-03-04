package uz.nt.uzumproject.rest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.model.source.Method;
import org.springframework.hateoas.Link;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

import java.util.List;
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UsersResources {
    private final UsersService usersService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }
    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<UsersDto> deleteUser(@RequestParam Integer id){
        return  usersService.deleteUser(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<List<UsersDto>> getAllUsers(){

        return usersService.getAllUsers();
    }

    @PostMapping("login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        Link link = Link.of("/product","get-all-products");
        ResponseDto<String> response = usersService.login(loginDto);
        response.add(link);

        return response;
    }
}