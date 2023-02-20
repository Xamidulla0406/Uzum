package uz.nt.uzumproject.service.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderValidator.class)
public @interface GenderAnnotation {
    String message() default "Wrong gender. Valid gender: male/female";
}