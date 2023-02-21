package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import java.util.ArrayList;
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
                                    categoryMapper.toEntity(categoryDto)))
                    )
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();

        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .data(categoryDto)
                    .build();
        }
    }

    public ResponseDto<List<CategoryDto>> getCategoryById(Integer id) {
        List<Optional<Category>> categoriesList = new ArrayList<>();

        for (Optional<Category> category : categoryRepository.findByParentId(id)) {
            int subId = category.get().getId();
            categoriesList.add(category);

            List<Optional<Category>> subCategoriesList = categoryRepository.findByParentId(subId);
            categoriesList.addAll(subCategoriesList);

        }

        return ResponseDto.<List<CategoryDto>>builder()
                .code(OK_CODE)
                .success(true)
                .message(OK)
                .data(categoriesList.stream().map(c -> categoryMapper.toDto(c.get())).collect(Collectors.toList()))
                .build();
    }
}
