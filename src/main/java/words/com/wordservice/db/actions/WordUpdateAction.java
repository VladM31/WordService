package words.com.wordservice.db.actions;

import org.springframework.lang.Nullable;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

public record WordUpdateAction(
        String id,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        @Nullable
        String category,
        @Nullable
        String description,
        CEFR cefr
) {
}
