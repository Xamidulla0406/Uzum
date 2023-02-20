package uz.nt.uzumproject.dto;

import lombok.*;
import uz.nt.uzumproject.service.validator.GenderAnnotation;
import uz.nt.uzumproject.service.validator.PhoneNumberAnnotation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto {
    private Integer id;
    @PhoneNumberAnnotation
    private String phone;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    @GenderAnnotation
    private String gender;
    private String birthDate;
    private String password;
}