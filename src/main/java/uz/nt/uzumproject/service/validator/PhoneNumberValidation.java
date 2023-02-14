package uz.nt.uzumproject.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class PhoneNumberValidation implements ConstraintValidator<IsValidPhoneNumber, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> codes = List.of("33", "88", "90", "91", "93", "94", "95", "97", "98", "99",
                "71", "77", "50", "55");
        if(s.length() == 9 &&
                codes.contains(s.substring(0,2))) return true;
        return false;
    }
}
