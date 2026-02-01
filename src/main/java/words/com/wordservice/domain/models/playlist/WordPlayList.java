package words.com.wordservice.domain.models.playlist;

import lombok.Builder;
import org.springframework.lang.Nullable;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.words.PinnedWord;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TreeSet;


@Builder(toBuilder = true)
public record WordPlayList(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        List<PinnedWord> words,
        TreeSet<String> tags,
        TreeSet<CEFR> cefrs,
        @Nullable
        Language language,
        @Nullable
        Language translateLanguage,
        @Nullable
        String associationId
) {
}
