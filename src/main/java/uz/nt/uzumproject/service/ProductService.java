package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.repository.ProductRepositoryImpl;
import uz.nt.uzumproject.rest.ProductResources;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final ProductMapper productMapper;
    private final ProductRepositoryImpl productRepositoryImpl;

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

    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts (Integer page, Integer size) {
        Long count = productRepository.count();
        Page<EntityModel<ProductDto>> products = productRepository
                .findAll(PageRequest.of((count / size) <= page ? (count % size == 0 ? (int)(count / size)  - 1 : (int)(count / size)) : page, size))
                .map(p -> {
                    EntityModel<ProductDto> entityModel = EntityModel.of(productMapper.toDto(p));
                    try {
                        entityModel.add(linkTo(ProductResources.class
                                .getDeclaredMethod("getProductById", Integer.class))
                                .withSelfRel()
                                .expand(p.getId()));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;
                });

        return ResponseDto.<Page<EntityModel<ProductDto>>>builder()
                .message(OK)
                .success(true)
                .data(products)
                .build();
    }
    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
        List<ProductDto> productDtoList = productRepository.getExpensiveProducts().stream()
                .map(productMapper::toDto)
                .toList();
        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .data(productDtoList)
                .build();
    }

    public ResponseDto<ProductDto> getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        return ResponseDto.<ProductDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(productMapper.toDto(product.get()))
                .build();
    }

//    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto) {
//        productRepository.
//    }

    public ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params) {
        Page<Product> productList = productRepositoryImpl.universalSearch(params);
        if (productList.isEmpty()){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        return ResponseDto.<Page<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(productList.map(productMapper::toDto))
                .build();
    }

    public ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort) {
        List<Sort.Order> orders = sort.stream()
                .map(s -> new Sort.Order(Sort.Direction.DESC,s))
                .toList();
        List<ProductDto> products = productRepository.findAll(Sort.by(orders)).stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .success(true)
                .data(products)
                .build();
        
    }
}
