package words.com.wordservice.api.requests.words;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import words.com.wordservice.api.requests.interfaces.LanguagesGetter;
import words.com.wordservice.api.valid.annotation.DifferentLanguage;
import words.com.wordservice.api.valid.annotation.NullableNotBlank;
import words.com.wordservice.api.valid.annotation.NullableSize;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

@DifferentLanguage
public record CreateWordRequest(
        @NotBlank(message = "Word is blank")
        @Size(min = 1, max = 255, message = "Word must be between 1 and 255 characters")
        String original,
        @NotNull(message = "Language is required")
        Language lang,


        @NotBlank(message = "Translation is blank")
        @Size(min = 1, max = 255, message = "Translation language must be 2 characters")
        String translate,
        @NotNull(message = "Translation language is required")
        Language translateLang,


        @Nullable
        @NullableSize(min = 1, max = 255, message = "Category must be between 1 and 255 characters")
        @NullableNotBlank(message = "Category is blank")
        String category,
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Sound File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Sound File Name is blank")
        String soundFileName,
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Sound File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Sound File Name is blank")
        String imageFileName,

        @Nullable
        @NullableSize(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
        @NullableNotBlank(message = "Description is blank")
        String description,
        @NotNull(message = "CEFR is required")
        CEFR cefr) implements LanguagesGetter {
    @Override
    public Language getLang() {
        return lang;
    }

    @Override
    public Language getTranslateLang() {
        return translateLang;
    }
}