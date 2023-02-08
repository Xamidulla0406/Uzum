package uz.nt.uzumproject.service.mapper;

import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setAmount(productDto.getAmount());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setIsAvailable(productDto.getAmount()>0?true:false);
        return product;
    }

    public static ProductDto toProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setAmount(product.getAmount());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setIsAvailable(product.getIsAvailable());
        return productDto;
    }


    public static List<ProductDto> productDtoList(List<Product> product){
        return product.stream().map(product1 -> toProductDto(product1)).collect(Collectors.toList());
    }
}
