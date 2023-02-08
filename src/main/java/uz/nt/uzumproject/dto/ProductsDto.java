package uz.nt.uzumproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsDto {
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private String imageUrl;
    private Boolean isAvailable;
}
