package words.com.wordservice.api.responds.playlist;

import org.springframework.lang.Nullable;
import words.com.wordservice.api.responds.UserWordRespond;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TreeSet;

public record PlayListRespond(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        List<PinnedWordRespond> words,
        TreeSet<String> tags,
        TreeSet<CEFR> cefrs,
        @Nullable
        Language language,
        @Nullable
        Language translateLanguage,
        @Nullable
        String associationId
) {

    public record PinnedWordRespond(
            Long learningGrade,
            OffsetDateTime createdAt,
            OffsetDateTime lastReadDate,
            UserWordRespond word
    ) {
    }


}
