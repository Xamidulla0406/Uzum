package uz.nt.uzumproject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginDto {
    private String username;
    private String password;
}
