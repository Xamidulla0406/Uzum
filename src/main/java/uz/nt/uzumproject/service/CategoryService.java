package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    public ResponseDto<CategoryDto> addCategory(CategoryDto dto) {
        Optional<Category> categoryDtoOptional = null;

            if(dto.getParentId() != null){
                categoryDtoOptional = categoryRepository.findById(dto.getParentId());
            }

           if( dto.getParentId() != null && categoryDtoOptional == null ){
               System.out.println("hato");
               return ResponseDto.<CategoryDto>builder()
                       .message(PARENT_ID_NOT_FOUND)
                       .code(PARENT_ID_NOT_FOUND_CODE)
                       .build();
           }



        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(dto))))
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();

        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }

    }


    public List<List<CategoryDto>> getByParentId(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isEmpty()){
            System.out.println("null");
            return null;
        }
//
//        List<List<CategoryDto>> list = categoryRepository.findAllByParentId(id).stream().map(categoryMapper::toDto).toList().stream()
//                .flatMap(c -> categoryRepository.findAllByParentId(c.getId()).stream().map(categoryMapper::toDto))
//                .map(Collections::singletonList)
//                .toList();

        List<CategoryDto> dtoList = categoryRepository.findAllByParentId(id).stream().map(categoryMapper::toDto).toList();
        System.out.println(dtoList.toString());



        List<List<CategoryDto>> list = dtoList.stream()
                .flatMap(c -> categoryRepository.findAllByParentId(c.getId()).stream().map(categoryMapper::toDto))
                .map(Collections::singletonList)
                .collect(Collectors.toList());


        Map<CategoryDto, List<CategoryDto>> map = dtoList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        c -> categoryRepository.findAllByParentId(c.getId()).stream().map(categoryMapper::toDto).collect(Collectors.toList())
                ));

        System.out.println(map);

        return list;
    }

    public Map<CategoryDto,List<CategoryDto>> get(Integer id){
        List<CategoryDto> dtoList = categoryRepository.findAllByParentId(id).stream().map(categoryMapper::toDto).toList();
        System.out.println(dtoList.toString());

        Map<CategoryDto, List<CategoryDto>> map = dtoList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        c -> categoryRepository.findAllByParentId(c.getId()).stream().map(categoryMapper::toDto).collect(Collectors.toList())
                ));

     return map;
    }


}
