package uz.nt.uzumproject.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private String imageUrl;
}
