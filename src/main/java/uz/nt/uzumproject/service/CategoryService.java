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

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        Optional<Category> optional = categoryRepository.findById(categoryDto.getParentId());
        if (optional.isPresent()) {
            categoryMapper.toDto(
                    categoryRepository.save(
                            categoryMapper.toEntity(categoryDto)
                    )
            );
        }
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto((
                            categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(optional.isPresent() ? OK : "parentId is not found")
                    .success(optional.isPresent())
                    .code(optional.isPresent() ? 0 : NOT_FOUND_ERROR_CODE)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }


}
