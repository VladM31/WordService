package words.com.wordservice.api.valid.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import words.com.wordservice.api.valid.validators.NullableNotBlankValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.TYPE_PARAMETER,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NullableNotBlankValidator.class)
public @interface NullableNotBlank {
    String message() default "Invalid value, must not be blank or be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
