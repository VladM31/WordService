package words.com.wordservice.api.responds;

import words.com.wordservice.domain.models.enums.WordType;

import java.time.OffsetDateTime;

public record UserWordRespond(
        String id,
        String userId,
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate,
        WordType type,
        WordRespond word
) {
}