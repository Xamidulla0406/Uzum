package uz.nt.uzumproject.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNumberValidation.class)
public @interface IsValidPhoneNumber {
    String message() default "Invalid phoneNumber";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
