package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.AuthorityService;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityResources{
    private final AuthorityService authorityService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE')")
    public ResponseDto<Void> addAuthority(@RequestParam String username, @RequestParam String authority){
        return authorityService.addAuthorityToUser(username, authority);
    }
}
