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
public class ProductDto {

    private Integer id;
    @NotEmpty(message =EMPTY_STRING)
    @NotNull(message = NULL_VALUE)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    @NotNull(message = NULL_VALUE)
    private Integer price;
    @PositiveOrZero(message = NEGATIVE_VALUE)
    @NotNull(message = NULL_VALUE)
    private Integer amount;
    @NotEmpty(message =EMPTY_STRING)
    @NotNull(message = NULL_VALUE)
    private String description;
    private String imageUrl;
    private Boolean isAvailable;
}
