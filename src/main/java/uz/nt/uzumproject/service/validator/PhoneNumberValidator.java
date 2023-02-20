package uz.nt.uzumproject.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberAnnotation, String> {

    List<String> phoneNumberCodes = List.of("33", "50", "55", "71", "77", "88", "90",
            "91", "93", "94", "95", "97", "98", "99");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() == 9 && phoneNumberCodes.contains(s.substring(0, 2));
    }
}