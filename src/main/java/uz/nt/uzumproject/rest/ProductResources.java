package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductService service;
    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return service.post(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateUser(@RequestBody ProductDto productDto){
        return service.patch(productDto);
    }
    @GetMapping
    public ResponseDto<List<ProductDto>> getAll(){
        return service.get();
    }
}
