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
    @NotNull(message = NULL_VALUE)
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
    private String imageUrl;
    private Boolean isAvailable;
    @NotNull(message = NULL_VALUE)
    @NotBlank(message = EMPTY_STRING)
    private Integer CategoryId;
}
