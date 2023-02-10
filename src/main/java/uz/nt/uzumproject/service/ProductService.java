package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.mapper.ProductMapperManual;
import uz.nt.uzumproject.service.validator.ProductValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final ProductMapper productMapper;


    public ResponseDto<ProductDto> add(ProductDto productDto) {
        if(!productValidator.productValidator(productDto).isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .errors(productValidator.productValidator(productDto))
                    .build();
        }
        Product product = productMapper.toEntity(productDto);
        product.setIsAvailable(true);
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .code(OK_CODE)
                .success(true)
                .message("OK")
                .data(productMapper.toDto(product))
                .build();

    }
        public ResponseDto<ProductDto> update (ProductDto productDto){
            if (productDto.getId() == null) {
                return ResponseDto.<ProductDto>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message("ID is null")
                        .build();
            }

            Optional<Product> productOptional = productRepository.findById(productDto.getId());

            if (productOptional.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .code(-1)
                        .message("Product with this id not found")
                        .build();
            }
            Product product = productOptional.get();
            if (productDto.getAmount() < 0) {
                return ResponseDto.<ProductDto>builder()
                        .success(false)
                        .code(-2)
                        .message("wrong input amount of product")
                        .build();
            }
            if (productDto.getName() != null) {
                product.setName(productDto.getName());
            }
            if (productDto.getAmount() != null) {
                product.setAmount(productDto.getAmount());
            }
            if (productDto.getAmount() != null) {
                product.setAmount(productDto.getAmount());
            }
            if (productDto.getPrice() != null) {
                product.setPrice(productDto.getPrice());
            }
            if (productDto.getDescription() != null) {
                product.setDescription(productDto.getDescription());
            }
            if (productDto.getIsAvailable() != null) {
                product.setIsAvailable(productDto.getIsAvailable());
            }
            try {
                if (product.getAmount() <= 0) {
                    product.setIsAvailable(false);
                }
                productRepository.save(product);

                return ResponseDto.<ProductDto>builder()
                        .success(true)
                        .data(productMapper.toDto(product))
                        .build();
            } catch (Exception e) {
                return ResponseDto.<ProductDto>builder()
                        .code(1)
                        .message("Error while saving product: " + e.getMessage())
                        .build();
            }

        }

        public ResponseDto<List<ProductDto>> getAllProducts () {
            return ResponseDto.<List<ProductDto>>builder()
                    .code(0)
                    .message("OK")
                    .success(true)
                    .data(productRepository.findAll().stream().map(p-> productMapper.toDto(p)).collect(Collectors.toList()))
                    .build();
        }
    }
