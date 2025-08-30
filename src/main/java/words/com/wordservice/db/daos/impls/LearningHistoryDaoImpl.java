package words.com.wordservice.db.daos.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.daos.LearningHistoryDao;
import words.com.wordservice.db.entities.history.CountLearningHistoryEntity;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.entities.history.StatisticsLearningHistoryEntity;
import words.com.wordservice.db.searches.history.CountLearningHistorySearch;
import words.com.wordservice.db.searches.history.LearningHistorySearch;
import words.com.wordservice.db.searches.history.StatisticsLearningHistorySearch;
import words.com.wordservice.utils.Range;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
class LearningHistoryDaoImpl implements LearningHistoryDao {
    private final LearningHistoryRepository repository;
    @Override
    public List<StatisticsLearningHistoryEntity> findBy(StatisticsLearningHistorySearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    @Override
    public List<LearningHistoryEntity> findBy(LearningHistorySearch search) {
        return repository.findAll(search);
    }

    @Override
    public List<CountLearningHistoryEntity> findBy(CountLearningHistorySearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    @Override
    public Page<StatisticsLearningHistoryEntity> findBy(StatisticsLearningHistorySearch search, Pageable pageable) {
        return repository.findStatisticsBy(
                CollectionUtils.isEmpty(search.getUserIds()),
                CollectionUtils.isEmpty(search.getUserIds()) ? null : search.getUserIds(),
                Range.from(search.getDate()),
                Range.to(search.getDate()),
                pageable);
    }

    @Override
    public Page<LearningHistoryEntity> findBy(LearningHistorySearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    public Page<CountLearningHistoryEntity> findBy(CountLearningHistorySearch search, Pageable pageable) {
        return repository.findCountBy(
                CollectionUtils.isEmpty(search.getUserIds()),
                CollectionUtils.isEmpty(search.getUserIds()) ? null : search.getUserIds(),
                pageable);
    }

    @Override
    public void saveAll(Collection<LearningHistoryEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(LearningHistorySearch search) {
        repository.delete(search);
    }
}
