package uz.nt.uzumproject.rest;

import jakarta.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<ProductDto> add(@RequestBody ProductDto productDto){

        return productService.add(productDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<ProductDto> update(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseDto<List<ProductDto>> getAll(){
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseDto<ProductDto> getProductById(@PathVariable Integer id , HttpServletRequest req){

        return productService.getProductById(id);
    }
}
