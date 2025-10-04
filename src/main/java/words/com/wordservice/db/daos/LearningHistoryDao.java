package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.entities.history.CountLearningHistoryProjection;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.entities.history.StatisticsLearningHistoryProjection;
import words.com.wordservice.db.searches.history.CountLearningHistorySearch;
import words.com.wordservice.db.searches.history.LearningHistorySearch;
import words.com.wordservice.db.searches.history.StatisticsLearningHistorySearch;

import java.util.Collection;
import java.util.List;

public interface LearningHistoryDao {

    List<StatisticsLearningHistoryProjection> findBy(StatisticsLearningHistorySearch search);

    List<LearningHistoryEntity> findBy(LearningHistorySearch search);

    List<CountLearningHistoryProjection> findBy(CountLearningHistorySearch search);

    Page<StatisticsLearningHistoryProjection> findBy(StatisticsLearningHistorySearch search, Pageable pageable);

    Page<LearningHistoryEntity> findBy(LearningHistorySearch search, Pageable pageable);

    Page<CountLearningHistoryProjection> findBy(CountLearningHistorySearch search, Pageable pageable);

    void saveAll(Collection<LearningHistoryEntity> entities);

    void delete(LearningHistorySearch search);
}
