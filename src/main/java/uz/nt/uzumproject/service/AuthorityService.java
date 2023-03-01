package uz.nt.uzumproject.service;

import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;
import uz.nt.uzumproject.security.UserAuthorities;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

@Service
public record AuthorityService(AuthorityRepository authorityRepository) {

    public ResponseDto<Void> addAuthorityToUser(String username, String authority) {
        if(authorityRepository.existsByUsernameAndAuthority(username, authority)) {
            return ResponseDto.<Void>builder()
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .message(AppStatusMessages.DUPLICATE_ERROR)
                    .build();
        }
        Authorities auth = new Authorities();
        auth.setAuthority(authority);
        auth.setUsername(username);
        authorityRepository.save(auth);

        return ResponseDto.<Void>builder()
                .message(AppStatusMessages.OK)
                .success(true)
                .code(AppStatusCodes.OK_CODE)
                .build();


    }
}
