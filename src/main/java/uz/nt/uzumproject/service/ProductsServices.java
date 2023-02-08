package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductsDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.ProductsRepository;
import uz.nt.uzumproject.service.mapper.ProductsMapper;
import uz.nt.uzumproject.service.mapper.UsersMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsServices {
    private final ProductsRepository repository;

    public ResponseDto<ProductsDto> addProduct(ProductsDto productsDto) {
        Product product = ProductsMapper.toEntity(productsDto);
        repository.save(product);

        return ResponseDto.<ProductsDto>builder()
                .success(true)
                .data(ProductsMapper.toDto(product))
                .message("OK")
                .build();
    }


    public ResponseDto<ProductsDto> updateProduct(ProductsDto productsDto) {
        if (productsDto.getId() == null) {
            return ResponseDto.<ProductsDto>builder()
                    .message("UserID is null")
                    .code(-2)
                    .data(productsDto)
                    .build();
        }

        Optional<Product> productOptional = repository.findById(productsDto.getId());

        if (productOptional.isEmpty()) {
            return ResponseDto.<ProductsDto>builder()
                    .message("User with ID " + productsDto.getId() + " is not found")
                    .code(-1)
                    .data(productsDto)
                    .build();
        }


        Product product = productOptional.get();
        if (productsDto.getAmount() != null) {
            product.setAmount(productsDto.getAmount());
        }
        if (productsDto.getName() != null) {
            product.setName(productsDto.getName());
        }
        if (productsDto.getDescription() != null) {
            product.setDescription(productsDto.getDescription());
        }
        if (productsDto.getPrice() != null) {
            product.setPrice(productsDto.getPrice());
        }
        if (productsDto.getImageUrl() != null) {
            product.setImageUrl(productsDto.getImageUrl());
        }
        if (productsDto.getIsAvailable() != null) {
            product.setIsAvailable(productsDto.getIsAvailable());
        }
        if (productsDto.getId() != null) {
            product.setId(productsDto.getId());
        }


        try {
            repository.save(product);

            return ResponseDto.<ProductsDto>builder()
                    .data(ProductsMapper.toDto(product))
                    .success(true)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductsDto>builder()
                    .data(ProductsMapper.toDto(product))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<List<ProductsDto>> getAllProducts() {
        return ResponseDto.<List<ProductsDto>>builder()
                .code(1)
                .message("All products")
                .success(true)
                .data(repository.findAll()
                        .stream()
                        .map(p -> ProductsMapper.toDto(p))
                        .collect(Collectors.toList()))
                .build();
    }
}

