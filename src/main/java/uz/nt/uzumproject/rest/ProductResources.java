package uz.nt.uzumproject.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDto<ProductDto> addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }

    @GetMapping()
    public ResponseDto<Page<EntityModel<ProductDto>>>getAllProducts(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size){
        return productService.getAllProducts(page, size);
    }
    @GetMapping("by-id")
    public ResponseDto<ProductDto>getProductById(@RequestParam Integer id){
        return productService.getProductById(id);
    }
    @GetMapping("expensive-by-category")
    public ResponseDto<List<ProductDto>> getExpensiveProducts(){
        return productService.getExpensiveProducts();
    }
    @GetMapping("sort")
    public ResponseDto<List<ProductDto>> getProducts(@RequestParam List<String> sort){
        return productService.getAllProductsWithSort(sort);
    }
//    @GetMapping("search")
//    public ResponseDto<List<ProductDto>> universalSearch(ProductDto productDto){
//        return productService.universalSearch(productDto);
//    }
    @GetMapping("search-2")
    public ResponseDto<Page<ProductDto>> universalSearch(@RequestParam Map<String, String> params){
        return productService.universalSearch2(params);
    }
}
