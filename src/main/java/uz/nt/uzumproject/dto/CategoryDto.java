package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer id;
    @NotBlank(message = NULL_VALUE)
    @Size(min = 3, max = 500)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    @Max(value = 500)
    private Integer parentId;
}
