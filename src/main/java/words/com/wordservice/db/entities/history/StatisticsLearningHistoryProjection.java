package words.com.wordservice.db.entities.history;

import words.com.wordservice.domain.models.enums.LearningHistoryType;

import java.time.LocalDate;

public interface StatisticsLearningHistoryProjection {
    int getCount();

    long getGrades();

    LearningHistoryType getType();

    LocalDate getDate();
}
