package words.com.wordservice.api.valid.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import words.com.wordservice.api.requests.interfaces.LanguagesGetter;
import words.com.wordservice.api.valid.annotation.DifferentLanguage;

import java.util.Objects;

public class DifferentLanguageValidator implements ConstraintValidator<DifferentLanguage, LanguagesGetter> {
    @Override
    public boolean isValid(LanguagesGetter languagesGetter, ConstraintValidatorContext constraintValidatorContext) {
        return !Objects.equals(languagesGetter.getLang(), languagesGetter.getTranslateLang());
    }
}
