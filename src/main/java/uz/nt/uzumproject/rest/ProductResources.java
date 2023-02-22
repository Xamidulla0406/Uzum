package uz.nt.uzumproject.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'SUPERADMIN')")
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto){
        UsersDto user = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return productService.addProduct(productDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @GetMapping()
    public ResponseDto<List<ProductDto>> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id, HttpServletRequest req){
        String authorization = req.getHeader("Authorization");

        return productService.getProductById(id);
    }

}
