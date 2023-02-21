package uz.nt.uzumproject.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class LoginDto {
    private String username;
    private String password;
}
