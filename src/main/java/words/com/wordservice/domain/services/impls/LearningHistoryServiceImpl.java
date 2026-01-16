package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.daos.LearningHistoryDao;
import words.com.wordservice.db.daos.UserWordDao;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.searches.Operations;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.domain.mappers.LearningHistoryDomainMapper;
import words.com.wordservice.domain.mappers.LearningHistorySearchMapper;
import words.com.wordservice.domain.models.filters.history.CountLearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.LearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.StatisticsLearningHistoryFilter;
import words.com.wordservice.domain.models.history.CountLearningHistory;
import words.com.wordservice.domain.models.history.LearningHistory;
import words.com.wordservice.domain.models.history.StatisticsLearningHistory;
import words.com.wordservice.domain.services.LearningHistoryService;

import java.util.HashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class LearningHistoryServiceImpl implements LearningHistoryService {
    private final LearningHistoryDomainMapper learningHistoryMapper;
    private final LearningHistoryDao learningHistoryDao;
    private final UserWordDao userWordDao;
    private final LearningHistorySearchMapper learningHistorySearchMapper;

    @Override
    public Page<LearningHistory> findBy(LearningHistoryFilter filter) {
        var histories = learningHistoryDao.findBy(
                learningHistorySearchMapper.toSearch(filter),
                learningHistorySearchMapper.toPageable(filter)
        );
        var wordIds = histories.stream().map(LearningHistoryEntity::getWordId).collect(Collectors.toSet());


        var wordById = new HashMap<String, UserWordEntity>();

        var search = UserWordSearch.builder()
                .wordIds(wordIds)
                .userWordIds(wordIds)
                .operation(Operations.OR)
                .build();
        var words = userWordDao.findBy(search);
        words.forEach(e -> wordById.put(e.getWord().getId(), e));
        words.forEach(e -> wordById.put(e.getId(), e));

        return histories.map(e -> learningHistoryMapper.toModel(e, wordById.get(e.getWordId())));
    }

    @Override
    public Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter) {
        return learningHistoryDao.findBy(learningHistorySearchMapper.toSearch(filter), Pageable.unpaged())
                .map(learningHistoryMapper::toStatisticsModel);
    }

    @Override
    public Page<CountLearningHistory> findBy(CountLearningHistoryFilter filter) {
        return learningHistoryDao.findBy(learningHistorySearchMapper.toSearch(filter), Pageable.unpaged())
                .map(learningHistoryMapper::toModel);
    }
}
