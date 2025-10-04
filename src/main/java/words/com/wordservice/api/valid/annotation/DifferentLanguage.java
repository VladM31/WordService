package words.com.wordservice.api.valid.annotation;

import jakarta.validation.Constraint;
import words.com.wordservice.api.valid.validators.DifferentLanguageValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.TYPE_PARAMETER,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DifferentLanguageValidator.class)
public @interface DifferentLanguage {
    String message() default "Invalid value, must not the same language";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
