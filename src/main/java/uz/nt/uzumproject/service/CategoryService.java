package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

   private final CategoryRepository categoryRepository;

   private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        if(categoryDto.getParentId() != null){
                   if(categoryRepository.findById(categoryDto.getParentId()).isEmpty()){
                       return ResponseDto.<CategoryDto>builder().code(AppStatusCodes.NOT_FOUND_CODE).message(AppStatusMessages.NOT_FOUND).build();
                   }
        }

        return ResponseDto.<CategoryDto>builder().code(AppStatusCodes.OK_CODE).data(categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)))).success(true).message(AppStatusMessages.OK).build();

    }
}
