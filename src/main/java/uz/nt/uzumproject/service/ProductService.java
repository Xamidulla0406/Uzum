package uz.nt.uzumproject.service;

import jakarta.persistence.criteria.Order;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public ResponseDto<ProductDto> add(ProductDto productDto) {
        List<ErrorDto> error = productValidator.getError(productDto);

        if(!error.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .message(VALIDATION_ERROR)
                    .error(error)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Product product = productMapper.toEntity(productDto);
        product.setIsAvailable(true);
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .message(OK)
                    .data(productMapper.toDto(product))
                    .code(OK_CODE)
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
                        .code(VALIDATION_ERROR_CODE)
                        .message(UNEXPECTED_ERROR)
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
                        .message(OK)
                        .data(productMapper.toDto(product))
                        .build();
            } catch (Exception e) {
                return ResponseDto.<ProductDto>builder()
                        .code(DATABASE_ERROR_CODE)
                        .message("Error while saving product: " + e.getMessage())
                        .build();
            }

        }

        public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts (Integer page, Integer size) {
        Long count = productRepository.count();

        Page<EntityModel<ProductDto>> products = productRepository.findAll(PageRequest.of(
                count / size <= page ? (count % size == 0 ? (int) (count/size) - 1 : (int) (count/size)) : page, size)
                )
                .map(product -> {
                    EntityModel<ProductDto> entityModel = EntityModel.of(productMapper.toDto(product));
                    try {
                        entityModel.add(linkTo(ProductResources.class
                                .getDeclaredMethod("getProductById", Integer.class))
                                .withSelfRel()
                                .expand(product.getId()));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return  entityModel;
                });

            return ResponseDto.<Page<EntityModel<ProductDto>>>builder()
                    .message(OK)
                    .success(true)
                    .data(products)
                    .build();
        }

    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(products))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message("Product with id " + id + " not found")
                        .code(-1)
                        .build()
                );
    }

    public ResponseDto<List<ProductDto>> getExpensiveProduct() {
        return ResponseDto.<List<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(productRepository.getExpensiveProducts().stream()
                        .map(productMapper::toDto).toList())
                .build();
    }

    public ResponseDto<List<ProductDto>> getExpensiveProduct2() {
        return ResponseDto.<List<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(productRepository.getExpensiveProducts2().stream()
                        .map(productMapper::toDto).toList())
                .build();
    }

    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto) {
        List<Product> products = productRepository.findProducts(
                productDto.getId(), productDto.getName(), productDto.getAmount(), productDto.getPrice());
        if(products.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<List<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .data(products.stream().map(productMapper::toDto).toList())
                .build();
    }

    public ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params) {
        Page<Product> products = productRepositoryImpl.universalSearch(params);
        if(products.isEmpty()){
            return ResponseDto.<Page<ProductDto>>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<Page<ProductDto>>builder()
                .code(OK_CODE)
                .data(products.map(productMapper::toDto))
                .message(OK)
                .build();
    }

    public ResponseDto<List<ProductDto>> getAllProductWithSort(List<String> sort) {
        List<Sort.Order> orders = sort.stream()
                .map(s -> new Sort.Order(Sort.Direction.DESC, s))
                .toList();

        List<ProductDto> products = productRepository.findAll(Sort.by(orders)).stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .data(products)
                .build();
    }
}

