package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductsRepository;
import uz.nt.uzumproject.service.mapper.ProductMap;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.List;
import java.util.Optional;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductMap productMap;
    private final ProductsRepository repository;
    private final ProductValidator productValidator;
    public ResponseDto<List<ProductDto>> getAll(){
        return ResponseDto.<List<ProductDto>>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .data(repository.findAll().stream().map(productMap::toProductDto).toList())
                .build();
    }
    public ResponseDto<ProductDto> addProduct(ProductDto productDto){
        List<ErrorDto> errors=productValidator.saving(productDto);
        if(!errors.isEmpty()){
           return ResponseDto.<ProductDto>builder()
                    .errorDtoList(errors)
                    .message(UNEXPECTED_ERROR)
                    .code(UNEXPECTED_ERROR_CODE)
                    .build();
        }
        repository.save(productMap.toProduct(productDto));
        return ResponseDto.<ProductDto>builder()
                .message("Success")
                .code(0)
                .success(true)
                .build();
    }
    public ResponseDto<ProductDto> editProducts(ProductDto productDto){
        List<ErrorDto> errors=productValidator.editing(productDto);
        if(!errors.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .errorDtoList(errors)
                    .message(VALIDATION_ERROR)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        Optional<Product> productOptional = repository.findById(productDto.getId());
        if(productOptional.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(productDto)
                    .build();
        }

        Product productEntity = productOptional.get();
        if (productDto.getName() != null){
            productEntity.setName(productDto.getName());
        }
        if (productDto.getPrice() != null){
            productEntity.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null){
            productEntity.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null){
            productEntity.setDescription(productDto.getDescription());
        }
//        if (product.getImageUrl() != null){
//            productEntity.setImages(product.getImageUrl());
//        }
        if (productDto.getIsAvailable() != null){
            productEntity.setIsAvailable(productDto.getIsAvailable());
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
