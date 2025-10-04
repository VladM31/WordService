package words.com.wordservice.api.valid.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import words.com.wordservice.api.valid.validators.NullableSizeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.TYPE_PARAMETER,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NullableSizeValidator.class)
public @interface NullableSize {
    String message() default "Invalid value, must be between {min} and {max} or be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}
