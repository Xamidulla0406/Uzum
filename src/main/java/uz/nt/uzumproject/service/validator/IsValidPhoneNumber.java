package uz.nt.uzumproject.service.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  NumberCheck.class)
@Documented
public @interface IsValidPhoneNumber {

    String massage() default "invalid phone number namuna: 987654321";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
