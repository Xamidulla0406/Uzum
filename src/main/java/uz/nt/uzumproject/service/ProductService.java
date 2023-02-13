package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;
import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository repository;
    private final ProductMapper mapper;

    public ResponseDto<ProductDto> add(ProductDto productDto) {
        productDto.setId(null);
        Product product = repository.save(mapper.toEntity(productDto));
        return ResponseDto.<ProductDto>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(mapper.toDto(product))
                .build();
    }


    public ResponseDto<ProductDto> edit(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message(AppStatusMessages.VALIDATION_ERROR)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .data(productDto)
                    .build();
        }
        Optional<Product> optional = repository.findFirstById(productDto.getId());
        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_CODE)
                    .data(productDto)
                    .build();
        } else if (productDto.getAmount() < 0) {
            return ResponseDto.<ProductDto>builder()
                    .message(AppStatusMessages.VALIDATION_ERROR)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .data(productDto)
                    .build();
        }

        ProductDto product = new ProductDto();

        product.setId(optional.get().getId());

        if (productDto.getName() != null) {
            product.setName(product.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(product.getPrice());
        }
        if (productDto.getAmount() != null) {
            product.setAmount(product.getAmount());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(product.getDescription());
        }
        if (productDto.getImageUrl() != null) {
            product.setImageUrl(product.getImageUrl());
        }
        if ((productDto.getAmount() != null && productDto.getAmount() > 0) || product.getAmount() > 0) {
            product.setIsAvailable(true);
        }

        try {

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(mapper.toDto(repository.save(mapper.toEntity(productDto))))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message(AppStatusMessages.DATABASE_ERROR)
                    .build();
        }
    }


    public ResponseDto<List<ProductDto>> get() {
        return ResponseDto.<List<ProductDto>>builder()
                .success(true)
                .code(OK_CODE)
                .message(OK)
                .data(repository.findByIsAvailable(true).stream().map(mapper::toDto).toList())
                .build();
    }

    public ResponseDto<List<ProductDto>> getByCategoryId(Integer id) {
        List<Product> products = repository.findByCategoryId(id);
        return ResponseDto.<List<ProductDto>>builder()
                .code(products.size() == 0 ? NOT_FOUND_CODE : OK_CODE)
                .message(products.size() == 0 ? NOT_FOUND : OK)
                .success(products.size() > 0)
                .data(products.stream().map(mapper::toDto).collect(Collectors.toList()))
                .build();
    }
}
