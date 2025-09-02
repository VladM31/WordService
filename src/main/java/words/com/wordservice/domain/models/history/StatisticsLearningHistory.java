package words.com.wordservice.domain.models.history;


import words.com.wordservice.domain.models.enums.LearningHistoryType;

import java.time.LocalDate;

public record StatisticsLearningHistory(
        int count,
        long grades,
        LearningHistoryType type,
        LocalDate date
) {
}
