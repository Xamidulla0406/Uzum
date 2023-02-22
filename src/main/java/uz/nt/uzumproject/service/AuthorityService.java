package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;
import uz.nt.uzumproject.repository.UsersRepository;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final UsersRepository usersRepository;
    public ResponseDto<Void> addAuthorityToUser(String username, String authorityName) {
        if (authorityRepository.existsByUsernameAndAuthority(username, authorityName)){
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }

        Authorities auth = new Authorities();
        auth.setUsername(username);
        auth.setAuthority(authorityName);
        authorityRepository.save(auth);



        return ResponseDto.<Void>builder()
                .message(OK)
                .success(true)
                .build();

    }
}
