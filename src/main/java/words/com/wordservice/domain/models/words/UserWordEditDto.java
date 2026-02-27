package words.com.wordservice.domain.models.words;

import org.springframework.lang.Nullable;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

public record UserWordEditDto(
        String id,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        @Nullable
        String category,
        @Nullable
        String soundFileName,
        @Nullable
        String imageFileName,
        @Nullable
        String description,
        CEFR cefr
) {
}
