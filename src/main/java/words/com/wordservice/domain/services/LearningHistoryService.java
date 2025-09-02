package words.com.wordservice.domain.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.domain.models.filters.history.CountLearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.LearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.StatisticsLearningHistoryFilter;
import words.com.wordservice.domain.models.history.CountLearningHistory;
import words.com.wordservice.domain.models.history.LearningHistory;
import words.com.wordservice.domain.models.history.StatisticsLearningHistory;

public interface LearningHistoryService {

    Page<LearningHistory> findBy(LearningHistoryFilter filter);

    Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter);

    Page<CountLearningHistory> findBy(CountLearningHistoryFilter filter);
}
