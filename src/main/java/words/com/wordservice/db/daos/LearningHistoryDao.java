package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.entities.history.CountLearningHistoryEntity;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.entities.history.StatisticsLearningHistoryEntity;
import words.com.wordservice.db.searches.history.CountLearningHistorySearch;
import words.com.wordservice.db.searches.history.LearningHistorySearch;
import words.com.wordservice.db.searches.history.StatisticsLearningHistorySearch;

import java.util.Collection;
import java.util.List;

public interface LearningHistoryDao {

    List<StatisticsLearningHistoryEntity> findBy(StatisticsLearningHistorySearch search);

    List<LearningHistoryEntity> findBy(LearningHistorySearch search);

    List<CountLearningHistoryEntity> findBy(CountLearningHistorySearch search);

    Page<StatisticsLearningHistoryEntity> findBy(StatisticsLearningHistorySearch search, Pageable pageable);

    Page<LearningHistoryEntity> findBy(LearningHistorySearch search, Pageable pageable);

    Page<CountLearningHistoryEntity> findBy(CountLearningHistorySearch search, Pageable pageable);

    void saveAll(Collection<LearningHistoryEntity> entities);

    void delete(LearningHistorySearch search);
}
