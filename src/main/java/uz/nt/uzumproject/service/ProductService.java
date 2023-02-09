package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ProductsDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.model.Users;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;
import uz.nt.uzumproject.service.mapper.UsersMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseDto<ProductsDto> addProduct(ProductsDto dto) {
        Product product = ProductMapper.toEntity(dto);
        productRepository.save(product);

        return ResponseDto.<ProductsDto>builder()
                .success(true)
                .data(ProductMapper.toDto(product))
                .message("OK")
                .build();
    }

    public ResponseDto<ProductsDto> updateProduct(ProductsDto productsDto) {
        if (productsDto.getId() == null){
            return ResponseDto.<ProductsDto>builder()
                    .message("Product is not found with " + productsDto.getId() + " id")
                    .code(-1)
                    .data(productsDto)
                    .build();
        }

        Optional<Product> productOptional = productRepository.findById(productsDto.getId());

        if (productOptional.isEmpty()) {
            return ResponseDto.<ProductsDto>builder()
                    .message("Product with ID " + productsDto.getId() + " is not found")
                    .code(-1)
                    .data(productsDto)
                    .build();
        }
    }
}