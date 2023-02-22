package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;

    @NotBlank(message = EMPTY_STRING)
    @Size(min = 3, max = 100, message = SIZE_MISMATCH)
    private String name;

    @Positive(message = NEGATIVE_VALUE)
    @Max(value = 500, message = SIZE_MISMATCH)
    private Integer parentId;
}
