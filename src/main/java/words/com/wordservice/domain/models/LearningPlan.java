package words.com.wordservice.domain.models;

import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

import java.time.OffsetDateTime;

public record LearningPlan(
        String userId,
        int wordsPerDay,
        Language nativeLang,
        Language learningLang,
        CEFR cefr,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
