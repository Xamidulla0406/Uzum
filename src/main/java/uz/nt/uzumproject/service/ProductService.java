package uz.nt.uzumproject.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.repository.ProductRepositoryImpl;
import uz.nt.uzumproject.rest.ProductResources;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.AppStatusMessages;
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

    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        List<ErrorDto> errors = productValidator.validateProduct(productDto);

        if (!errors.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .errors(errors)
                    .data(productDto)
                    .message(VALIDATION_ERROR)
                    .code(VALIDATION_ERROR_CODE)
                    .success(false)
                    .build();
        }

        Product product = productMapper.toEntity(productDto);

        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .success(true)
                .code(0)
                .data(productMapper.toDto(product))
                .message("OK")
                .build();
    }

    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
        List<ProductDto> products = productRepository.getExpensiveProducts2().stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(products)
                .build();
    }

    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(-2)
                    .build();
        }

        Optional<Product> optional = productRepository.findById(productDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        Product product = optional.get();

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null && productDto.getPrice() > 0) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null && productDto.getAmount() > 0) {
            product.setIsAvailable(true);
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }

        try {

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    public ResponseDto<Page<EntityModel<ProductDto>>> getAllProducts(Integer page, Integer size) {
        int count =  (int) productRepository.count();
        page = count / size <= page ? count/size -1 : page;
        Page<EntityModel<ProductDto>> products = productRepository.findAll(PageRequest.of(page,size))
                .map(p -> {
                    EntityModel<ProductDto> entityModel = EntityModel.of(productMapper.toDto(p));

                    try {
                        entityModel.add(WebMvcLinkBuilder.linkTo(ProductResources.class.getDeclaredMethod("getProductById", Integer.class, HttpServletRequest.class))
                                .withSelfRel()
                                .expand(p.getId()));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;
                });

        return ResponseDto.<Page<EntityModel<ProductDto>>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(products)
                .build();
    }
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(products))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message(NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build()
                );
    }

    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto) {
        List<Product> products = productRepository.findProductById(productDto.getId(), productDto.getName(), productDto.getAmount(), productDto.getPrice());
        if (products.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        return ResponseDto.<List<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .data(products.stream().map(productMapper::toDto).toList())
                .build();
    }

    public ResponseDto<Page<ProductDto>> universalSearch2(Map<String, String> params) {
        Page<Product> products = productRepositoryImpl.universalSearch(params);

        if (products.isEmpty()){
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        return ResponseDto.<Page<ProductDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .data(products.map(productMapper::toDto))
                .build();
    }

    public ResponseDto<List<ProductDto>> getAllProductsWithSort(List<String> sort){
        List<Sort.Order> orders = sort.stream()
                .map(s-> new Sort.Order(Sort.Direction.DESC,s))
                .toList();

        List<ProductDto> products = productRepository.findAll(Sort.by(orders)).stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseDto.<List<ProductDto>>builder()
                .data(products)
                .message(OK)
                .code(0)
                .success(true)
                .build();
    }
}
