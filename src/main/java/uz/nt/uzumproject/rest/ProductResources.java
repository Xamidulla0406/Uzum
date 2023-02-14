package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
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
    public ResponseDto<ProductDto> add(@RequestBody ProductDto productDto){

        return productService.add(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> update(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }

    @GetMapping("/get-all-product")
    public ResponseDto<List<ProductDto>> getAll(){

        return productService.getAllProducts();
    }


}
