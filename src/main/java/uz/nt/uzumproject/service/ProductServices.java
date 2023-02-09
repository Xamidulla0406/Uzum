package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServices {
    private final ProductRepository repository;

    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        repository.save(product);

        return ResponseDto.<ProductDto>builder()
                .success(true)
                .data(ProductMapper.toDto(product))
                .message("OK")
                .build();
    }


    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("UserID is null")
                    .code(-2)
                    .data(productDto)
                    .build();
        }

        Optional<Product> productOptional = repository.findById(productDto.getId());

        if (productOptional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .message("User with ID " + productDto.getId() + " is not found")
                    .code(-1)
                    .data(productDto)
                    .build();
        }


        Product product = productOptional.get();
        if (productDto.getAmount() != null) {
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getIsAvailable() != null) {
            product.setIsAvailable(productDto.getIsAvailable());
        }
        if (productDto.getId() != null) {
            product.setId(productDto.getId());
        }


        try {
            repository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .data(ProductMapper.toDto(product))
                    .success(true)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .data(ProductMapper.toDto(product))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<List<ProductDto>> getAllProducts() {
        return ResponseDto.<List<ProductDto>>builder()
                .code(1)
                .message("All products")
                .success(true)
                .data(repository.findAll()
                        .stream()
                        .map(p -> ProductMapper.toDto(p))
                        .collect(Collectors.toList()))
                .build();
    }
}

