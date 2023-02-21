package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

@Service
@RequiredArgsConstructor
public class AuthorityService {


    AuthorityRepository authorityRepository;

    public ResponseDto<Void> addAuthorityToUser(String name, String authority){
//        if(authorityRepository.existsByUsernameAnAndAuthority(name,authority)){
//            return ResponseDto.<Void>builder()
//                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
//                    .message(AppStatusMessages.DUPLICATE_ERROR)
//                    .build();
//        }
        Authorities authorities = new Authorities();
        authorities.setUsername(name);
        authorities.setAuthority(authority);
        authorityRepository.save(authorities);

        return ResponseDto.<Void>builder()
                .message(AppStatusMessages.OK)
                .success(true)
                .build();
    }
}
