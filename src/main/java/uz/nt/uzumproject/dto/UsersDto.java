package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private String password;
    private Date birthDate;
}