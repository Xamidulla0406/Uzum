package uz.nt.uzumproject.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        UsersDto user = (UsersDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return productService.addProduct(productDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @GetMapping("sort")
    public ResponseDto<List<ProductDto>> getAllProducts(@RequestParam List<String> sort) {
        return productService.getAllProducts(sort);
    }

    @GetMapping("by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id, HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");

        return productService.getProductById(id);
    }

    @GetMapping("search")
    public ResponseDto<Page<Product>> universalSearch(@RequestParam Map<String, String> map) {
        return productService.universalSearch(map);

    }
}