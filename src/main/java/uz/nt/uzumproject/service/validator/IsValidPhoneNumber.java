package uz.nt.uzumproject.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = PhoneNumberValidation.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidPhoneNumber {
    String message() default "Invalid phoneNumber";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}