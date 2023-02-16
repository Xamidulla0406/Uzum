package uz.nt.uzumproject.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderValidation.class)
public @interface ValidGender {
    String message() default "Invalid Gender";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
