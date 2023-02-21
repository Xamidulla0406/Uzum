package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.AuthorityService;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityResources {
    AuthorityService authorityService;
    @PostMapping
    public ResponseDto<Void> addAuthorityToUser(@RequestParam String username, String authority){
        return authorityService.addAuthorityToUser(username,authority);
    }

}
