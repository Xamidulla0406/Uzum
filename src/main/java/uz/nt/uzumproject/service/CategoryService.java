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

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        Category category = mapper.toEntity(categoryDto);
        Optional<Category> first = repository.findAll().stream()
                .filter(a -> a.getId().equals(category.getId()) || a.getName().equals(category.getName()))
                .findFirst();
        if (first.isPresent()) {
            return ResponseDto.<CategoryDto>builder()
                    .message(ALREADY_EXISTS)
                    .code(ALREADY_EXISTS_CODE)
                    .data(categoryDto)
                    .build();
        }
        try {
            repository.save(category);
            return ResponseDto.<CategoryDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(mapper.toDto(repository.save(category)))
                    .build();
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .message(UNEXPECTED_ERROR+" "+e.getMessage())
                    .code(UNEXPECTED_ERROR_CODE)
                    .data(mapper.toDto(category))
                    .build();
        }
    }

    public ResponseDto<List<CategoryDto>> getCategories() {
        return ResponseDto.<List<CategoryDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList()))
                .build();
    }

    public ResponseDto<List<CategoryDto>> getCategoriesByCategoryId(Integer id) {
        List<Category> categories=repository.findAllByParentId(id);
        return ResponseDto.<List<CategoryDto>>builder()
                .code(categories.size()==0?NOT_FOUND_CODE:OK_CODE)
                .message(categories.size()==0?NOT_FOUND:OK)
                .success(categories.size()>0)
                .data(categories.size()>0?categories.stream().map(mapper::toDto).collect(Collectors.toList()):null)
                .build();
    }
}
