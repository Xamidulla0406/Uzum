package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.uzumproject.model.Category;
;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    @Positive(message = NEGATIVE_VALUE )
    private Integer price;
    @Positive(message = NEGATIVE_VALUE)
    private Integer amount;
    @NotBlank(message = EMPTY_STRING)
    private String description;
    private String imageUrl;

    private CategoryDto category;


}
