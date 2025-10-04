package words.com.wordservice.api.valid.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import words.com.wordservice.api.valid.validators.StringInValidator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.TYPE_PARAMETER,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StringInValidator.class)
public @interface StringIn {
    String message() default "Invalid value, must be one of {values}";

    boolean canBeNull() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};
}
