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
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {

        if (productDto.getAmount() < 0){
            productDto.setAmount(0);
        }

        Product product = ProductMapper.toEntity(productDto);

        if (product.getAmount() != null && product.getAmount() > 0){
            product.setIsAvailable(true);
        }else {
            product.setIsAvailable(false);
        }
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .success(true)
                .code(0)
                .data(ProductMapper.toDto(product))
                .message("OK")
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
                    .code(-1)
                    .message("Product with ID " + productDto.getId() + " is not found!")
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
        if (productDto.getImageUrl() != null) {
            product.setImageUrl(productDto.getImageUrl());
        }
        try {

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message("OK")
                    .data(ProductMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message("Error while saving product: " + e.getMessage())
                    .code(1)
                    .build();
        }
    }

    public ResponseDto<List<ProductDto>> getAllProducts() {
        return ResponseDto.<List<ProductDto>>builder()
                .message("OK")
                .code(0)
                .success(true)
                .data( productRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList()))
                .build();
    }
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(ProductMapper.toDto(products))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message("Product with id " + id + " not found")
                        .code(-1)
                        .build()
                );
    }
}
