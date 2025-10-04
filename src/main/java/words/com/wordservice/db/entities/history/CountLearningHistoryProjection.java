package words.com.wordservice.db.entities.history;


import words.com.wordservice.domain.models.enums.LearningHistoryType;


public interface CountLearningHistoryProjection {
    int getCount();
    LearningHistoryType getType();
}
