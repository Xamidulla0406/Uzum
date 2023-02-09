package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.service.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductsService service;
    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        return service.addProduct(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateUser(@RequestBody ProductDto productDto){
        return service.editProducts(productDto);
    }
    @GetMapping
    public ResponseDto<List<ProductDto>> getAll(){
        return service.getAll();
    }
}
