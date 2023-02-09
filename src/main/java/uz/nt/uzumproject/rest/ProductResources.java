package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.ProductServices;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductServices service;

    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto){
        return service.addProduct(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return service.updateProduct(productDto);
    }

    @GetMapping
    public ResponseDto<List<ProductDto>> getAllProducts() {
        return service.getAllProducts();
    }
}
