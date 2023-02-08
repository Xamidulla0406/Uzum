package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Product;
import uz.nt.uzumproject.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @PostMapping
    public ResponseDto<ProductDto> add(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @GetMapping("/get-all-product")
    public List<ResponseDto<ProductDto>> getAll(){
        return productService.getAllProduct();
    }


}
