package words.com.wordservice.api.requests.plan;

import jakarta.validation.constraints.*;
import words.com.wordservice.api.requests.interfaces.LanguagesGetter;
import words.com.wordservice.api.valid.annotation.DifferentLanguage;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

@DifferentLanguage(message = "Native language and learning language should be different")
public record LearningPlanRequest(
        @Min(value = 1, message = "Words per day should be more than 0")
        @Max(value = 100, message = "Words per day should be less than 100")
        int wordsPerDay,
        @NotNull(message = "Native language should not be null")
        Language nativeLang,
        @NotNull(message = "Learning language should not be null")
        Language learningLang,
        @NotNull(message = "CEFR level should not be null")
        CEFR cefr
) implements LanguagesGetter {
    @Override
    public Language getLang() {
        return nativeLang;
    }

    @Override
    public Language getTranslateLang() {
        return learningLang;
    }
}