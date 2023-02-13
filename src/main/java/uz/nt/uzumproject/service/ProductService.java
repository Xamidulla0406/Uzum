package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.List;
import java.util.Optional;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final ProductMapper productMapper;

    public ResponseDto<ProductDto> addProduct(ProductDto dto){
        List<ErrorDto> errors = productValidator.productValidator(dto);
        if (!errors.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .message(VALIDATION_ERROR)
                    .errors(errors)
                    .code(VALIDATION_ERROR_CODE)
                    .success(false)
                    .build();
        }

        Product product = productMapper.toEntity(dto);
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .build();
    }

    public ResponseDto<ProductDto> updateProduct(ProductDto dto){
        if(dto.getId() == null){
            return ResponseDto.<ProductDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(dto)
                    .build();
        }

        Optional<Product> optionalProduct = productRepository.findById(dto.getId());
        if (optionalProduct.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .code(-NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .success(false)
                    .data(dto)
                    .build();
        }
        Product product = optionalProduct.get();
        product.setIsAvailable(true);
        product.setId(dto.getId());
        if(dto.getDescription() != null){
            product.setDescription(dto.getDescription());
        }

        if(dto.getPrice() != null){
            product.setPrice(dto.getPrice());
        }
        if(dto.getAmount() != null){
            product.setAmount(dto.getAmount());
            product.setIsAvailable(dto.getAmount() > 0);
        }
        if(dto.getName() != null){
            product.setName(dto.getName());
        }

        try {
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(dto)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .data(productMapper.toDto(product))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<List<Product>> getAllProduct(){
        List<Product> productList = productRepository.findAll();
        return ResponseDto.<List<Product>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(productList)
                .build();
    }
}
