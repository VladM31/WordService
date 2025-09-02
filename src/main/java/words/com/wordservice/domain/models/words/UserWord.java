package words.com.wordservice.domain.models.words;

import lombok.Builder;
import words.com.wordservice.domain.models.enums.WordType;

import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record UserWord(
        String id,
        String userId,
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate,
        WordType type,
        Word word
) {
}
