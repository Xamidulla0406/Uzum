package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setAmount(productDto.getAmount());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setIsAvailable(true);

        return product;
    }

    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setAmount(product.getAmount());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setIsAvailable(product.getIsAvailable());

        return productDto;
    }
}
