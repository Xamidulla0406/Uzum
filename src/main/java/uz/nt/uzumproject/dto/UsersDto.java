package uz.nt.uzumproject.dto;

import lombok.*;
import uz.nt.uzumproject.service.validator.IsValidPhoneNumber;
import uz.nt.uzumproject.service.validator.ValidGender;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    @IsValidPhoneNumber
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    //@ValidGender
    private String gender;
    private String password;
    private boolean enabled;
    private Date birthDate;
}