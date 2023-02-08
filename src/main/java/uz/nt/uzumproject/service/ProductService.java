package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepository;
import uz.nt.uzumproject.service.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        if(product.getAmount() < 0 || product.getPrice() < 0){
            return ResponseDto.<ProductDto>builder()
                    .data(ProductMapper.toDto(product))
                    .code(2)
                    .message("Xato ma'lumot")
                    .build();
        }
        productRepository.save(product);


        return ResponseDto.<ProductDto>builder()
                .data(ProductMapper.toDto(product))
                .message("OK")
                .success(true)
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
