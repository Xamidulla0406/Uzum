package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDto {




    private Integer id;
    @NotBlank(message = NULL_VALUE)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    private Integer parentId;
}
