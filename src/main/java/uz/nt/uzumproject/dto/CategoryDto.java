package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "")
    @Size(max = 100, min = 3, message = "")
    private String name;
    @Positive(message = "")
//    @NotNull(message = "")
    @Max(value = 500, message = "")
    private Integer parentId;
}
