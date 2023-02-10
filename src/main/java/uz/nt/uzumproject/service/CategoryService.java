package uz.nt.uzumproject.service;

import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

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
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentId());
            if (optionalCategory.isEmpty()) {
                return ResponseDto.<CategoryDto>builder()
                        .message("Category not found with this ParentId: "+Integer.toString(categoryDto.getParentId()))
                        .code(VALIDATION_ERROR_CODE)
                        .build();
            }
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto))))
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": "+e.getMessage())
                    .build();
        }
    }

    public ResponseDto<List<CategoryDto>> byId(Integer id) {
        if (id==null){
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()){
            return ResponseDto.<List<CategoryDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR)
                    .build();
        }
        List<Category> list = new ArrayList<>();
//        Integer c_id=id;
//        while(c_id!=null) {
//            for (Category category : categoryRepository.findAll()) {
//                if (c_id == null) {
//                    break;
//                }
//                if (category.getId().equals(c_id)) {
//                    list.add(category);
//                    c_id = category.getParentId();
//                }
//            }
//        }
        list.addAll(categoryRepository.findAllByParentId(id));
        return ResponseDto.<List<CategoryDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(list.stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                .build();
    }
}
