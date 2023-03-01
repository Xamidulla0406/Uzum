package uz.nt.uzumproject.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.util.List;

public class NumberCheck implements ConstraintValidator<IsValidPhoneNumber, String> {

    private final List<String> numbers=List.of("33","55","99","88","90","93","94","95","97","98","77");
    @Override
    public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Long.parseLong(o);
            return numbers.contains(o.substring(0, 2)) && o.length() == 9;
        }catch (Exception e){
            return false;
        }
    }
}
