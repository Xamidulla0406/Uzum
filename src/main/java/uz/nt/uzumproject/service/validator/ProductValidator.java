package uz.nt.uzumproject.service.validator;

import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.ErrorDto;
import uz.nt.uzumproject.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Component
public class ProductValidator {

    public List<ErrorDto> getError(ProductDto productDto) {
        List<ErrorDto> error = new ArrayList<>();

        if(productDto.getAmount() == null){
            error.add(new ErrorDto("Amount", NULL_VALUE));
        }else if(productDto.getAmount() <= 0){
            error.add(new ErrorDto("Amount", NEGATIVE_VALUE));
        }

        if(productDto.getDescription() == null){
            error.add(new ErrorDto("Description", NULL_VALUE));
        } else if (productDto.getDescription().length() == 0) {
            error.add((new ErrorDto("Description", EMPTY_STRING)));
        }

        if(productDto.getPrice() == null){
            error.add(new ErrorDto("Price", NULL_VALUE));
        } else if (productDto.getPrice() == 0) {
            error.add((new ErrorDto("Price", NEGATIVE_VALUE)));
        }

        if(productDto.getName() == null){
            error.add(new ErrorDto("Name", NULL_VALUE));
        } else if (productDto.getName().length() == 0) {
            error.add((new ErrorDto("Name", EMPTY_STRING)));
        }

//        if(productDto.getImageUrl() == null){
//            error.add(new ErrorDto("ImageUrl", NULL_VALUE));
//        } else if (productDto.getImageUrl().length() == 0) {
//            error.add((new ErrorDto("ImageUrl", EMPTY_STRING)));
//        }
        return error;
    }
}
