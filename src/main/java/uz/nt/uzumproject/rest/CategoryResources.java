package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto dto){
        return categoryService.addCategory(dto);
    }


    @GetMapping("get-by-parent-id")
    public List<List<CategoryDto>> getByParentId(@RequestParam Integer id){
        return categoryService.getByParentId(id);
    }

    @GetMapping("map")
    public Map<CategoryDto, List<CategoryDto>> get(@RequestParam Integer id){
        return categoryService.get(id);

    }


}
