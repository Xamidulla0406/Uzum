package uz.nt.uzumproject.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.uzumproject.model.Category;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    private String name;
    @NotNull(message = NULL_VALUE)
    @PositiveOrZero(message = NEGATIVE_VALUE)
    private Integer price;
    @NotNull(message = NULL_VALUE)
    @PositiveOrZero(message = NEGATIVE_VALUE)
    private Integer amount;
    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    private String description;
    private CategoryDto category;
    private String imageUrl;
    private Boolean isAvailable;
}
