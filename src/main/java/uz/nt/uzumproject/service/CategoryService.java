package uz.nt.uzumproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.rest.CategoryResources;
import uz.nt.uzumproject.service.mapper.CategoryMapper;
import uz.nt.uzumproject.service.mapper.CommonMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        if(categoryDto.getParentId() != null){
            if(categoryRepository.findFirstByParentId(categoryDto.getParentId()).isEmpty()){
                return ResponseDto.<CategoryDto>builder()
                        .success(false)
                        .code(VALIDATION_ERROR_CODE)
                        .data(categoryDto)
                        .message(VALIDATION_ERROR)
                        .build();
            }
        }
        try{
            return ResponseDto.<CategoryDto>builder()
                    .message(OK)
                    .data(categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto))))
                    .success(true)
                    .code(OK_CODE)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryDto)
                    .code(DATABASE_ERROR_CODE)
                    .success(false)
                    .message(DATABASE_ERROR)
                    .build();
        }
    }
}
