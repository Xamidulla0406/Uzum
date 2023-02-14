package uz.nt.uzumproject.dto;

import jakarta.validation.constraints.*;
import lombok.*;
<<<<<<< HEAD
import uz.nt.uzumproject.service.validator.AppStatusMessages;
=======

import java.util.ArrayList;
import java.util.List;
>>>>>>> dev/MuhammadAli

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
<<<<<<< HEAD
    @Size(max = 100, min = 3, message = SIZE_MISMATCH)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    @NotNull(message = NULL_VALUE)
    @Max(value = 500, message = SIZE_MISMATCH)
    private Integer parentId;
=======
    @Size(max = 100,min = 3,message = SIZE_MISMATCH)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    @Max(value = 500,message = SIZE_MISMATCH)
    private Integer parentId;
    private List<CategoryDto> subCategories;
>>>>>>> dev/MuhammadAli
}
