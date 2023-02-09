package uz.nt.uzumproject.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ProductsDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.ProductsServices;
import uz.nt.uzumproject.service.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductsResources {
    private final ProductsServices service;

    @PostMapping
    public ResponseDto<ProductsDto> addProduct(@RequestBody ProductsDto productsDto){
        return service.addProduct(productsDto);
    }

    @PatchMapping
    public ResponseDto<ProductsDto> updateProduct(@RequestBody ProductsDto productsDto){
        return service.updateProduct(productsDto);
    }

    @GetMapping
    public ResponseDto<List<ProductsDto>> getAllProducts() {
        return service.getAllProducts();

    }
}
