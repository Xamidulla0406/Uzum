package uz.nt.uzumproject.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    private String phone;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private String birthDate;

    private String password;

    private Boolean enabled;
    private List<ProductDto> product;
}