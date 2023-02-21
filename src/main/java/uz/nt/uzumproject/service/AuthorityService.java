package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class AuthorityService{
    private final AuthorityRepository authorityRepository;
    public ResponseDto<Void> addAuthority(String username, String authorityName) {
        if (authorityRepository.existsByUsernameAndAuthority(username, authorityName)){
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }

        return ResponseDto.<Void>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .build();
    }
}
