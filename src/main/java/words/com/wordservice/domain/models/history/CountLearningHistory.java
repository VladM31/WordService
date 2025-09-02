package words.com.wordservice.domain.models.history;


import words.com.wordservice.domain.models.enums.LearningHistoryType;

public record CountLearningHistory(
        int count,
        LearningHistoryType type
) {
}
