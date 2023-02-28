package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    public ResponseDto<Void> addAuthorityToUser(String username, String authority) {
        if(authorityRepository.existsByUsernameAndAuthority(username,authority)){
            return ResponseDto.<Void>builder()
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .message(AppStatusMessages.DUPLICATE_ERROR)
                    .success(false)
                    .build();
        }

        Authorities auth = new Authorities();
        auth.setUsername(username);
        auth.setAuthority(authority);

        authorityRepository.save(auth);

        return ResponseDto.<Void>builder()
                .code(AppStatusCodes.OK_CODE)
                .message(AppStatusMessages.OK)
                .success(true)
                .build();
    }
}
