package words.com.wordservice.api.responds;

import lombok.Builder;
import org.springframework.lang.Nullable;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;

import java.time.OffsetDateTime;


public record WordRespond(
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
