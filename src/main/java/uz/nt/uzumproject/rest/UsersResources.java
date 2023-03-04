package uz.nt.uzumproject.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.core.context.SecurityContextHolder;
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
@SecurityRequirement(name = "Authorization")
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

    @GetMapping("by-phone-number")
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }

    @PostMapping("login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto) throws NoSuchMethodException {
        Link link = Link.of("/product", "product-list");
        ResponseDto<String> resposeDto = usersService.login(loginDto);
        resposeDto.add(link);


        Method getUserByPhone = UsersResources.class.getDeclaredMethod("getUserByPhoneNumber", String.class);
        resposeDto.add(linkTo(getUserByPhone).withRel("user-by-phone-number").expand("+998906978087"));
        return resposeDto;
    }

    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getUserById(@PathVariable Integer id){
        return usersService.getById(id);
    }
}