package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @GetMapping("get-all-products")
    public ResponseDto<List<ProductDto>>getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("by-id")
    public ResponseDto<ProductDto>getProductById(@RequestParam Integer id){
        return productService.getProductById(id);
    }

}
