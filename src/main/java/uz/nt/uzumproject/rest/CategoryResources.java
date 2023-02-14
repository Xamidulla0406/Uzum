package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryService categoryService;
    @PostMapping
    public ResponseDto<CategoryDto> add(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<List<CategoryDto>> getParentId(@PathVariable Integer id){
        return categoryService.getParentId(id);
    }
}
