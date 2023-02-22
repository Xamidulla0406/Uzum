package uz.nt.uzumproject.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = GenderValidation.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidGender {
    String message() default "Invalid gender";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
