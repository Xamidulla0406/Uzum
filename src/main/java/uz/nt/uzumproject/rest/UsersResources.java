package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }
    @PostMapping("login")
    public ResponseDto<String> loginUser(@RequestBody LoginDto loginDto) throws NoSuchMethodException {
        Link link = Link.of("/product/", "product-list");
        ResponseDto<String> response = usersService.loginUser(loginDto);
        response.add(link);

        Method getUserByPhoneNumber = UsersResources.class
                .getDeclaredMethod("getUserByPhoneNumber", String.class);
        response.add(linkTo(getUserByPhoneNumber)
                .withRel("user-by-phone-number")
                .expand("+998919250620"));

        return response;
    }

    @DeleteMapping
    public ResponseDto<UsersDto> deleteUser(@RequestParam  Integer id){
        return usersService.deleteUser(id);
    }

    @GetMapping("users-list")
    private List<ResponseDto<UsersDto>> getUserById(){
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return usersService.getById(id);
    }
}