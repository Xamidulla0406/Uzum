package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setAmount(productDto.getAmount());
        product.setDescription(productDto.getDescription());
        return product;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setAmount(product.getAmount());
        productDto.setDescription(product.getDescription());
        return productDto;
    }
}