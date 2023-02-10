package uz.nt.uzumproject.service.validator;

import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Component
public class ProductValidator {

    public  List<ErrorDto> saving(ProductDto productDto){
        List<ErrorDto> errorDtoList = new ArrayList<>();

        if(productDto.getAmount() == null){
            errorDtoList.add(new ErrorDto("Amount",NULL_VALUE));
        } else if (productDto.getAmount()<=0) {
            errorDtoList.add(new ErrorDto("Amount",NEGATIVE_VALUE));
        }

        if(productDto.getPrice() == null){
            errorDtoList.add(new ErrorDto("Price",NULL_VALUE));
        } else if (productDto.getPrice()<=0) {
            errorDtoList.add(new ErrorDto("Price",NEGATIVE_VALUE));
        }

        if(productDto.getName() == null){
            errorDtoList.add(new ErrorDto("Name",NULL_VALUE));
        }
        else if (productDto.getName().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto("Name",EMPTY_STRING));
        }

        if(productDto.getDescription() == null){
            errorDtoList.add(new ErrorDto("Description",NULL_VALUE));
        } else if (productDto.getDescription().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto("Description",EMPTY_STRING));
        }


        return errorDtoList;
    }
    public List<ErrorDto> editing(ProductDto productDto){

        List<ErrorDto> errorDtoList = new ArrayList<>();
        if(productDto.getId()==null){
            errorDtoList.add(new ErrorDto("Id",NULL_VALUE));
        }
        if (productDto.getAmount()!=null&&productDto.getAmount()<0) {
            errorDtoList.add(new ErrorDto("Amount",NEGATIVE_VALUE));
        }

        if (productDto.getPrice()!=null&&productDto.getPrice()<0) {
            errorDtoList.add(new ErrorDto("Price",NEGATIVE_VALUE));
        }

        if (productDto.getName()!=null&&productDto.getName().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto("Name",EMPTY_STRING));
        }
        if (productDto.getDescription()!=null&&productDto.getDescription().trim().isEmpty()) {
            errorDtoList.add(new ErrorDto("Description",EMPTY_STRING));
        }

        return errorDtoList;
    }
}
