package uz.nt.uzumproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private CategoryDto category;
    private String imageUrl;
    private Boolean isAvailable;
}
