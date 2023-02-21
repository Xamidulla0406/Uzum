package uz.nt.uzumproject.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidation implements ConstraintValidator<ValidGender, String> {

    static String MALE = "Male";
    static String FEMALE = "Female";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (s.equals(MALE) || s.equals(FEMALE) || s.equals(null));
    }
}
