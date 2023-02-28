package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.repository.ProductRepositoryImpl;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final ProductMapper productMapper;
    private final ProductRepositoryImpl productRepositoryImpl;

    public ResponseDto<ProductDto> add(ProductDto productDto) {
        if(!productValidator.validateProduct(productDto).isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .errors(productValidator.validateProduct(productDto))
                    .build();
        }
        Product product = productMapper.toEntity(productDto);
        product.setIsAvailable(true);
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .code(OK_CODE)
                .success(true)
                .message(OK)
                .data(productMapper.toDto(product))
                .build();

    }
        public ResponseDto<ProductDto> update (ProductDto productDto){
            if (productDto.getId() == null) {
                return ResponseDto.<ProductDto>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message(NULL_VALUE)
                        .build();
            }

            Optional<Product> productOptional = productRepository.findById(productDto.getId());

            if (productOptional.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
            Product product = productOptional.get();
            if (productDto.getAmount() < 0) {
                return ResponseDto.<ProductDto>builder()
                        .success(false)
                        .code(VALIDATION_ERROR_CODE)
                        .message(NEGATIVE_VALUE)
                        .build();
            }
            if (productDto.getName() != null && !productDto.getName().equals("")) {
                product.setName(productDto.getName());
            }
            if (productDto.getAmount() != null && productDto.getAmount()>0) {
                product.setIsAvailable(true);
                product.setAmount(productDto.getAmount());
            }
            if (productDto.getPrice() != null && productDto.getPrice()>=0) {
                product.setPrice(productDto.getPrice());
            }
            if (productDto.getDescription() != null && !productDto.getDescription().equals("")) {
                product.setDescription(productDto.getDescription());
            }
            try {
                if (product.getAmount() == 0) {
                    product.setIsAvailable(false);
                }
                productRepository.save(product);

                return ResponseDto.<ProductDto>builder()
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .data(productMapper.toDto(product))
                        .build();
            } catch (Exception e) {
                return ResponseDto.<ProductDto>builder()
                        .code(DATABASE_ERROR_CODE)
                        .message(DATABASE_ERROR + e.getMessage())
                        .build();
            }

        }

        public ResponseDto<List<ProductDto>> getAllProducts () {
            return ResponseDto.<List<ProductDto>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(productRepository
                            .findAll().stream()
                            .map(p -> productMapper.toDto(p))
                            .collect(Collectors.toList()))
                    .build();
        }

    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(p->ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(p))
                        .success(true)
                        .message(OK)
                        .code(OK_CODE)
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .build());
    }

    public ResponseDto<List<ProductDto>> getExpensiveProducts() {
        List<ProductDto> products = productRepository.getExpensiveProductsWithHQL().stream()
                .map(productMapper::toDto)
                .toList();
        return ResponseDto.<List<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .data(products)
                .success(true)
                .build();
    }

    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto) {
        List<Product> products = productRepository.universalSearch(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getAmount());
        if(products.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .success(false)
                    .message(NOT_FOUND)
                    .build();
        }

        return ResponseDto.<List<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(products.stream().map(p->productMapper.toDto(p)).toList())
                .build();

    }

    public ResponseDto<List<ProductDto>> universalSearch2(Map<String, String> params) {
        List<Product> products = productRepositoryImpl.universalSearch(params);
        if(products.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .success(false)
                    .message(NOT_FOUND)
                    .build();
        }

        return ResponseDto.<List<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(products.stream().map(p->productMapper.toDto(p)).toList())
                .build();

    }
}
