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

@Service
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository productRepository;
    private final ProductMapper mapper;
    public ResponseDto<ProductDto> post(ProductDto productDto){
        productDto.setId(null);
        Product product = productRepository.save(mapper.toEntity(productDto));
        return ResponseDto.<ProductDto>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .success(true)
                .data(mapper.toDto(product))
                .build();
    }



    public ResponseDto<ProductDto> patch(ProductDto productDto){
        if(productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message(AppStatusMessages.VALIDATION_ERROR)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .data(productDto)
                    .build();
        }
        Optional<Product> optional = productRepository.findFirstById(productDto.getId());
        if(optional.isEmpty()){
            return ResponseDto.<ProductDto>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_CODE)
                    .data(productDto)
                    .build();
        } else if (productDto.getAmount()<0){
        return ResponseDto.<ProductDto>builder()
                .message(AppStatusMessages.VALIDATION_ERROR)
                .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                .data(productDto)
                .build();
        }

        ProductDto product = new ProductDto();

            product.setId(optional.get().getId());

            if(productDto.getName() != null){
                product.setName(product.getName());
            }
        if(productDto.getPrice() != null){
            product.setPrice(product.getPrice());
        }
        if(productDto.getAmount() != null){
            product.setAmount(product.getAmount());
        }
        if(productDto.getDescription() != null){
            product.setDescription(product.getDescription());
        }
        if(productDto.getImageUrl() != null){
            product.setImageUrl(product.getImageUrl());
        }
        if((productDto.getAmount() != null && productDto.getAmount()>0) || product.getAmount()>0){
            product.setIsAvailable(true);
        }

        try{

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .code(AppStatusCodes.OK_CODE)
                    .message(AppStatusMessages.OK)
                    .data(mapper.toDto(productRepository.save(mapper.toEntity(productDto))))
                    .build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message(AppStatusMessages.DATABASE_ERROR)
                    .build();
        }
    }



    public ResponseDto<List<ProductDto>> get(){
        return ResponseDto.<List<ProductDto>>builder()
                .success(true)
                .code(AppStatusCodes.OK_CODE)
                .message(AppStatusMessages.OK)
                .data(productRepository.findAll().stream().map(mapper::toDto).toList())
                .build();
    }
}
