package words.com.wordservice.domain.models.history;

import lombok.Builder;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.LearningHistoryType;

import java.time.LocalDate;

@Builder
public record LearningHistory(
        String id,
        String userId,
        String wordId,
        String original,
        Language nativeLang,
        Language learningLang,
        CEFR cefr,
        LocalDate date,
        LearningHistoryType type,
        int grade
) {
}
