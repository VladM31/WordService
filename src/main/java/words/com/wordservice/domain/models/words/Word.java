package words.com.wordservice.domain.models.words;

import lombok.Builder;
import org.springframework.lang.Nullable;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;

import java.time.OffsetDateTime;


@Builder(toBuilder = true)
public record Word(
        String id,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        @Nullable
        String category,
        @Nullable
        String soundLink,
        @Nullable
        String imageLink,
        WordType type,
        CEFR cefr,
        @Nullable
        String description,
        OffsetDateTime createdAt
) {

}
