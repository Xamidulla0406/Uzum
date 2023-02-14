package uz.nt.uzumproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        if(categoryDto.getParentId() != null){
            if(categoryRepository.findFirstById(categoryDto.getParentId()).isEmpty()){
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

    public ResponseDto<List<CategoryDto>> getParentId(Integer id) {
        try {
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(OK)
                    .success(true)
                    .code(OK_CODE)
                    .data(categoryRepository.findAllByParentId(id).stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                    .build();
        }catch (Exception e){
            return ResponseDto.<List<CategoryDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .success(false)
                    .message(DATABASE_ERROR + " " + e.getMessage())
                    .data(null)
                    .build();
        }
    }
}
