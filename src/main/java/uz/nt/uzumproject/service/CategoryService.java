package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build()
                    ;
        } catch (Exception e) {
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }

    public ResponseDto<List<CategoryDto>> getCategoryById(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(optional.stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                    .build();
        }else {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
    }
}
}
