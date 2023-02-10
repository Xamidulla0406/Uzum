package uz.nt.uzumproject.service.validator;

import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;



import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;


public class ProductValidator {

    public  List<ErrorDto> error(ProductDto productDto){
        List<ErrorDto> errorDtoList = new ArrayList<>();

        if(productDto.getAmount() == null){
            errorDtoList.add(new ErrorDto(VALIDATION_ERROR_CODE,VALIDATION_ERROR));
        } else if (productDto.getAmount()<=0) {
            errorDtoList.add(new ErrorDto(UNEXPECTED_ERROR_CODE,UNEXPECTED_ERROR));
        }

        if(productDto.getPrice() == null){
            errorDtoList.add(new ErrorDto(VALIDATION_ERROR_CODE,VALIDATION_ERROR));
        } else if (productDto.getPrice()<=0) {
            errorDtoList.add(new ErrorDto(UNEXPECTED_ERROR_CODE,UNEXPECTED_ERROR));
        }

        if(productDto.getName() == null){
            errorDtoList.add(new ErrorDto(VALIDATION_ERROR_CODE,VALIDATION_ERROR));
        }
        else if (productDto.getName().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto(UNEXPECTED_ERROR_CODE,UNEXPECTED_ERROR));
        }

        if(productDto.getDescription() == null){
            errorDtoList.add(new ErrorDto(VALIDATION_ERROR_CODE,VALIDATION_ERROR));
        } else if (productDto.getDescription().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto(UNEXPECTED_ERROR_CODE,UNEXPECTED_ERROR));
        }


        return errorDtoList;
    }
}
