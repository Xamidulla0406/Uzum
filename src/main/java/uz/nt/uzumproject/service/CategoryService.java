package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private  final CategoryMapper categoryMapper;
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            if(checkParentId(categoryDto.getParentId())) {
                return ResponseDto.<CategoryDto>builder()
                        .code(OK_CODE)
                        .message(OK)
                        .data(categoryMapper.
                                toDto(categoryRepository
                                        .save(categoryMapper
                                                .toEntity(categoryDto))))
                        .success(true)
                        .build();
            }
            else {
                return ResponseDto.<CategoryDto>builder()
                        .data(categoryDto)
                        .success(false)
                        .message("parent id not found: " + NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build();
            }
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .success(false)
                    .data(categoryDto)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": "+e.getMessage())
                    .build();
        }
    }

    public ResponseDto<List<CategoryDto>> getAllCategories() {
        return ResponseDto.<List<CategoryDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(categoryRepository
                        .getAllByParentId(null).stream()
                        .map(categoryMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
    public ResponseDto<List<CategoryDto>> getCategoryById(Integer id){
        Optional<Category> optionalCategory=categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return ResponseDto.<List<CategoryDto>>builder()
                    .success(false)
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }
        return ResponseDto.<List<CategoryDto>>builder()
                .code(OK_CODE)
                .data(categoryRepository
                        .getAllByParentId(id).stream()
                        .map(categoryMapper::toDto)
                        .collect(Collectors.toList()))
                .build();

    }

    public ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto) {
        if(categoryDto.getId()==null){
            return ResponseDto.<CategoryDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .success(false)
                    .build();
        }
        Optional<Category> optionalCategory=categoryRepository.findById(categoryDto.getId());
        if(optionalCategory.isEmpty()){
            return ResponseDto.<CategoryDto>builder()
                    .success(false)
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .data(categoryDto)
                    .build();
        }
        Category category= optionalCategory.get();
        if(categoryDto.getName()!=null){
            category.setName(categoryDto.getName());
        }
        if(categoryDto.getParentId()!=null && checkParentId(categoryDto.getParentId())){
            category.setParentId(categoryDto.getParentId());
        }
        categoryRepository.save(category);
        return ResponseDto.<CategoryDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(categoryMapper.toDto(category))
                .build();
    }
    public  boolean checkParentId(Integer id){
        return categoryRepository.findById(id).stream()
                .anyMatch(c -> c.getId().equals(id));

    }
}
