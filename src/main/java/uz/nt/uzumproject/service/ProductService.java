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

@Service
@RequiredArgsConstructor
public class ProductService {
    public final ProductRepository productRepository;
    public ResponseDto<ProductDto> post(ProductDto productDto){
        productRepository.save(ProductMapper.toEntity(productDto));
        return ResponseDto.<ProductDto>builder()
                .message("Success")
                .code(0)
                .success(true)
                .build();
    }



    public ResponseDto<ProductDto> patch(ProductDto productDto){
        if(productDto.getId() == null)
            return ResponseDto.<ProductDto>builder().message("Id is null").code(2).data(productDto).build();
        Optional<Product> optional = productRepository.findFirstById(productDto.getId());
        if(optional.isEmpty() || productDto.getAmount()<0)
        return ResponseDto.<ProductDto>builder().message("Id is not found or amount small zero").code(1).data(productDto).build();

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
                    .code(1)
                    .message("Succes")
                    .data(ProductMapper.toProductDto(productRepository.save(ProductMapper.toEntity(productDto))))
                    .build();
        }catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .code(2)
                    .message("Database error")
                    .build();
        }




    }



    public ResponseDto<List<ProductDto>> get(){
        return ResponseDto.<List<ProductDto>>builder().success(true).code(0).message("success").data(ProductMapper.productDtoList(productRepository.findByIsAvailable(true))).build();
    }
}
