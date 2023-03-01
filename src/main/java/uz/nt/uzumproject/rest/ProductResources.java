package uz.nt.uzumproject.rest;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.repository.ProductRepositoryImpl;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> add(@RequestBody ProductDto productDto){

        return productService.add(productDto);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<ProductDto> update(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }

    @GetMapping
    public ResponseDto<List<ProductDto>> getAll(){
        return productService.getAllProducts();
    }


    @GetMapping("by-id")
    public ResponseDto<ProductDto>getProductById(@RequestParam Integer id){
        return productService.getProductById(id);
    }

    @GetMapping("/get-expensive-product")
    public ResponseDto<List<ProductDto>> getExpensiveProduct(){
        return productService.getExpensiveProduct();
    }

    @GetMapping("/get-expensive-product2")
    public ResponseDto<List<ProductDto>> getExpensiveProduct2(){
        return productService.getExpensiveProduct();
    }

    @GetMapping("/search")
    public ResponseDto<List<ProductDto>> search(ProductDto productDto){
        return productService.universalSearch(productDto);
    }

    @GetMapping("/search2")
    public ResponseDto<List<ProductDto>> search2(@RequestParam Map<String, String> params){
        return productService.universalSearch2(params);
    }

}
