package words.com.wordservice.api.responds.plan;

import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

import java.time.OffsetDateTime;

public record LearningPlanRespond(
        int wordsPerDay,
        Language nativeLang,
        Language learningLang,
        CEFR cefr,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
