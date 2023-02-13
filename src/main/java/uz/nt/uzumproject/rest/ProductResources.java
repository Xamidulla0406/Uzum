package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
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
    public ResponseDto<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto) {
        return service.add(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateUser(@RequestBody ProductDto productDto) {
        return service.edit(productDto);
    }

    @GetMapping
    public ResponseDto<List<ProductDto>> get() {
        return service.get();
    }
    @GetMapping("by-category-id")
    public ResponseDto<List<ProductDto>> getByCategoryId(@RequestParam Integer id){
        return service.getByCategoryId(id);
    }
}
