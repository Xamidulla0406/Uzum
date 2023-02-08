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


    public ResponseDto<ProductDto> add(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        productRepository.save(product);

        return ResponseDto.<ProductDto>builder()
                .code(0)
                .success(true)
                .message("OK")
                .data(ProductMapper.toDto(product))
                .build();
    }

    public ResponseDto<ProductDto> update(ProductDto productDto) {
        if(productDto.getId() == null){
            return ResponseDto.<ProductDto>builder()
                    .code(-2)
                    .message("ID is null")
                    .build();
        }

        Optional<Product> product = productRepository.findById(productDto.getId());

        if(product.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .code(-1)
                    .message("Product with this id not found")
                    .build();
        }
        Product product1=product.get();
        if(product1.getName() != null){
            product1.setName(productDto.getName());
        }
        if(product1.getAmount() != null){
            product1.setAmount(productDto.getAmount());
        }
        if(product1.getAmount()!=null){
            product1.setAmount(productDto.getAmount());
        }
        if(product1.getPrice()!=null){
            product1.setPrice(productDto.getPrice());
        }
        if(product1.getDescription()!=null){
            product1.setDescription(productDto.getDescription());
        }
        if(product1.getIsAvailable()!=null){
            product1.setIsAvailable(productDto.getIsAvailable());
        }
        try {
            productRepository.save(product1);

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .data(ProductMapper.toDto(product1))
                    .build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .code(1)
                    .message("Error while saving product: " + e.getMessage())
                    .build();
        }

    }

    public ResponseDto<List<ProductDto>> getAllProducts() {
        return ResponseDto.<List<ProductDto>>builder()
                .code(0)
                .message("OK")
                .success(true)
                .data(productRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList()))
                .build();
    }

    public List<ResponseDto<ProductDto>> getAllProduct() {
        return productRepository.findAll().stream().
                map(s -> ResponseDto.<ProductDto>builder()
                        .message("OK")
                        .success(true)
                        .data(ProductMapper.toDto(s))
                        .build()).collect(Collectors.toList());
    }


}
