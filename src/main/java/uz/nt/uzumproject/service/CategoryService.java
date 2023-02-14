package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(categoryDto.getParentId());
        if (category.isPresent()){
            try {
                return ResponseDto.<CategoryDto>builder()
                        .data(categoryMapper.toDto(
                                categoryRepository.save(
                                        categoryMapper.toEntity(categoryDto)
                                )
                        ))
                        .message(OK)
                        .success(true)
                        .build();
            }catch (Exception e){
                return ResponseDto.<CategoryDto>builder()
                        .code(DATABASE_ERROR_CODE)
                        .message(DATABASE_ERROR + ": " + e.getMessage())
                        .data(categoryDto)
                        .build();
            }
        }
        return ResponseDto.<CategoryDto>builder()
                .code(-1)
                .message("Id with this " + categoryDto.getParentId() + "not found")
                .data(categoryDto)
                .build();
    }

    public ResponseDto<CategoryDto> getCategory(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        CategoryDto categoryDto = null;
        List<Category> list = categoryRepository.findAllByParentId(id);
        List<CategoryDto> categoryList = new ArrayList<>();
        if (optional.isPresent()){
            categoryDto = categoryMapper.toDto(optional.get());
            for (Category category : list) {
                categoryList.add(categoryMapper.toDto(category));
            }
            categoryDto.setSubCategories(categoryList);
        }

        return ResponseDto.<CategoryDto>builder()
                .message(optional.isPresent() ? OK : NOT_FOUND)
                .code(optional.isPresent() ? OK_CODE : NOT_FOUND_ERROR_CODE)
                .success(optional.isPresent() ? true : false)
                .data(categoryDto)
                .build();
    }
}
