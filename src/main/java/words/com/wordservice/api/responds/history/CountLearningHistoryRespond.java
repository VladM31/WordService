package words.com.wordservice.api.responds.history;

import words.com.wordservice.domain.models.enums.LearningHistoryType;

public record CountLearningHistoryRespond(
        int count,
        LearningHistoryType type
) {
}
