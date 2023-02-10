package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.EMPTY_STRING;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.NEGATIVE_VALUE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    private Integer parentId;
}
