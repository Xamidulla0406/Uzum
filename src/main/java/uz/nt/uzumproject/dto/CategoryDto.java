package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.uzumproject.service.validator.AppStatusMessages.*;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    Integer id;
    @NotBlank(message = EMPTY_STRING)
    @NotNull(message = NULL_VALUE)
    String name;

    @Positive(message = NEGATIVE_VALUE)
    Integer parentId;
}
