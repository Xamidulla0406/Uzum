package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;

import java.util.Optional;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public ResponseDto<Void> addAuthorityToUser(String username, String authority) {


        if(authorityRepository.existsAuthoritiesByUsernameAndAuthority(username,authority)){
            return ResponseDto.<Void>builder()
                    .code(DUPLICATE_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }

        authorityRepository.save(new Authorities(username,authority));

        return ResponseDto.<Void>builder()
                .message(OK)
                .code(OK_CODE)
                .build();
    }
}
