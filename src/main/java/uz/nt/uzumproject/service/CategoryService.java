package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.DATABASE_ERROR_CODE;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.DATABASE_ERROR;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.OK;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Value("{spring.datasource.")
    String url;



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
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }
}
