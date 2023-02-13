package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryResources {
    private final CategoryService service;
    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto dto){
        return service.addCategory(dto);
    }
    @GetMapping
    public ResponseDto<List<CategoryDto>> getCategories(){
        return service.getCategories();
    }
    @GetMapping("by-id")
    public ResponseDto<List<CategoryDto>> getCategoriesById(@RequestParam Integer id){
        return service.getCategoriesByCategoryId(id);
    }
}
