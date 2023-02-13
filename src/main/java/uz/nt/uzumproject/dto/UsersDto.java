package uz.nt.uzumproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto {
    private Integer id;
    private String phone;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private String birthDate;
    private List<ProductDto> product;
}