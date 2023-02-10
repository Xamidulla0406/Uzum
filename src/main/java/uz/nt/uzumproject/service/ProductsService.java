package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.ProductsRepository;
import uz.nt.uzumproject.rest.ProductResources;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository repository;
    public ResponseDto<List<ProductDto>> getAll(){
        return ResponseDto.<List<ProductDto>>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .data(repository.findAll().stream().map(ProductMapper::toProductDto).toList())
                .build();
    }
    public ResponseDto<ProductDto> addProduct(ProductDto productDto){
        ProductValidator productValidator = new ProductValidator();
        if(!productValidator.error(productDto).isEmpty()){
           return ResponseDto.<ProductDto>builder()
                    .errorDtoList(productValidator.error(productDto))
                    .message("Failed")
                    .code(2)
                    .build();
        }
        repository.save(ProductMapper.toEntity(productDto));
        return ResponseDto.<ProductDto>builder()
                .message("Success")
                .code(0)
                .success(true)
                .build();
    }
    public ResponseDto<ProductDto> editProducts(ProductDto product){
        if(product.getId()==null){
            return ResponseDto.<ProductDto>builder()
                    .code(-2)
                    .message("No ID")
                    .build();
        }
        Optional<Product> productOptional = repository.findById(product.getId());

        Product productEntity = productOptional.get();
        if (product.getName() != null){
            productEntity.setName(product.getName());
        }
        if (product.getPrice() != null){
            productEntity.setPrice(product.getPrice());
        }
        if (product.getAmount() != null){
            productEntity.setAmount(product.getAmount());
        }
        if (product.getDescription() != null){
            productEntity.setDescription(product.getDescription());
        }
//        if (product.getImageUrl() != null){
//            productEntity.setImages(product.getImageUrl());
//        }
        if (product.getIsAvailable() != null){
            productEntity.setIsAvailable(product.getIsAvailable());
        }
        try{
            repository.save(productEntity);
            return ResponseDto.<ProductDto>builder()
                    .code(-2)
                    .success(true)
                    .message("OK")
                    .data(ProductMapper.toProductDto(productEntity))
                    .build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                .code(-1)
                .message(e.getMessage())
                .build();
        }
    }
}
