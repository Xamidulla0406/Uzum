package uz.nt.uzumproject.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductResources {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<ProductDto> add(@RequestBody ProductDto productDto){

        return productService.add(productDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<ProductDto> update(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseDto<Page<EntityModel<ProductDto>>> getAll(@RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "1") Integer size){
        return productService.getAllProducts(page, size);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseDto<ProductDto> getProductById(@PathVariable Integer id , HttpServletRequest req){
        return productService.getProductById(id);
    }
    @GetMapping("/expensive-by-category")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
        return productService.getExpensiveProducts();
    }
    @GetMapping("/universal-search")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseDto<Page <ProductDto>> universalSearch2( @RequestParam Map<String,String> params){
        return productService.universalSearch2(params);
    }

    @GetMapping("/sort")
    public ResponseDto<List<ProductDto>> getSort(@RequestParam List<String> field){
        return productService.getSort(field);
    }

}
