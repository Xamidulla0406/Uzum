package uz.nt.uzumproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;

    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private String birthDate;
    private List<ProductDto> productDtos;
}