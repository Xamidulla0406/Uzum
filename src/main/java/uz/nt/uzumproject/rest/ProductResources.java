package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.ProductService;
import uz.nt.uzumproject.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductService productService;
    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return productService.post(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.patch(productDto);
    }


    @GetMapping()
    public ResponseDto<List<ProductDto>> get(){
        return productService.get();
    }
}
