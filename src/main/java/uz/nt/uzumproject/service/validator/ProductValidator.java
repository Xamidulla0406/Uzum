package uz.nt.uzumproject.service.validator;

import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;



import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Component
public class ProductValidator {

    public  List<ErrorDto> error(ProductDto productDto){
        List<ErrorDto> errorDtoList = new ArrayList<>();

        if(productDto.getAmount() == null){
            errorDtoList.add(new ErrorDto("Amount",NULL_VALUE));
        } else if (productDto.getAmount()<=0) {
            errorDtoList.add(new ErrorDto("Amount",NEGATIVE_VALUE));
        }

        if(productDto.getPrice() == null){
            errorDtoList.add(new ErrorDto("Price",NULL_VALUE));
        } else if (productDto.getPrice()<=0) {
            errorDtoList.add(new ErrorDto("Pricee",NEGATIVE_VALUE));
        }

        if(productDto.getName() == null){
            errorDtoList.add(new ErrorDto("Name",NULL_VALUE));
        }
        else if (productDto.getName().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto("Name",EMPTY_STRING));
        }

        if(productDto.getDescription() == null){
            errorDtoList.add(new ErrorDto("Desctiption",NULL_VALUE));
        } else if (productDto.getDescription().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto("Desctiption",EMPTY_STRING));
        }


        return errorDtoList;
    }
}
