package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {

    private final UsersService usersService;

    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("by-phone-number")
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto) throws NoSuchMethodException {
        Link link = Link.of("/product/", "product-list");
        ResponseDto<String> response = usersService.login(loginDto);
        response.add(link);

        Method getUserByPhoneNumber = UsersResources.class.getDeclaredMethod("getUserByPhoneNumber", String.class);

        response.add(linkTo(getUserByPhoneNumber).withRel("user-by-phone-number").expand("998949341454"));
        return response;
    }

    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return usersService.getById(id);
    }
}