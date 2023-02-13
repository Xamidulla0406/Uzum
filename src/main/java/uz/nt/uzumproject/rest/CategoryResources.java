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
@RequestMapping("category")
public class CategoryResources {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
    @GetMapping
    public ResponseDto<List<CategoryDto>> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @PatchMapping
    public ResponseDto<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }
    @GetMapping("/{id}")
    public ResponseDto<List<CategoryDto>> get(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }
}
