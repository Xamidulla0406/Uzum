package uz.nt.uzumproject.dto;
import lombok.Getter;
import lombok.Setter;
import uz.nt.uzumproject.model.Category;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private String imageUrl;
    private Boolean isAvailable;
    private CategoryDto category;
}
