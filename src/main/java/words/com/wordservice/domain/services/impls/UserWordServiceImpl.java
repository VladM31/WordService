package words.com.wordservice.domain.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import words.com.wordservice.db.daos.LearningHistoryDao;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.daos.UserWordDao;
import words.com.wordservice.db.daos.WordDao;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.searches.Operations;
import words.com.wordservice.db.searches.PinnedWordSearch;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.domain.mappers.UserWordDomainMapper;
import words.com.wordservice.domain.mappers.UserWordSearchMapper;
import words.com.wordservice.domain.models.filters.UserWordFilter;
import words.com.wordservice.domain.models.words.*;
import words.com.wordservice.domain.services.UserWordService;

import java.util.Collection;

@RequiredArgsConstructor
class UserWordServiceImpl implements UserWordService {
    private final WordDao wordDao;
    private final UserWordDao userWordDao;
    private final UserWordDomainMapper userWordDomainMapper;
    private final UserWordSearchMapper userWordSearchMapper;
    private final LearningHistoryDao learningHistoryDao;
    private final PinnedWordDao pinnedWordDao;

    @Override
    public Page<UserWord> findBy(UserWordFilter filter) {
        var search = userWordSearchMapper.toSearch(filter);
        var pageable = userWordSearchMapper.toPageable(filter);
        return userWordDao.findBy(search, pageable)
                .map(userWordDomainMapper::toModel);
    }

    @Override
    @Transactional
    public void save(Collection<ModifyUserWord> userWords) {
        var userWordEntities = userWords.stream()
                .map(userWordDomainMapper::toEntity)
                .toList();

        var wordEntities = userWordEntities.stream()
                .map(UserWordEntity::getWord)
                .toList();

        wordDao.saveAll(wordEntities);
        userWordDao.saveAll(userWordEntities);

        var historyEntities = userWords.stream()
                .map(userWordDomainMapper::toLearningHistoryEntity)
                .toList();
        learningHistoryDao.saveAll(historyEntities);
    }

    @Override
    public void savePins(Collection<PinUserWord> userWords) {
        var actions = userWords.stream()
                .map(userWordDomainMapper::toAction)
                .toList();
        userWordDao.upsertAll(actions);

        var historyEntities = userWords.stream()
                .map(userWordDomainMapper::toLearningHistoryEntity)
                .toList();
        learningHistoryDao.saveAll(historyEntities);
    }

    @Override
    public void update(Collection<ModifyUserWord> userWords) {
        var entities = userWords.stream()
                .map(userWordDomainMapper::toEntity)
                .toList();
        userWordDao.updateAll(entities);
    }

    @Override
    public void addGrades(Collection<GradeUserWord> grades) {
        var actions = grades.stream()
                .map(userWordDomainMapper::toAction)
                .toList();
        userWordDao.addGrades(actions);
    }

    @Override
    public long count(UserWordFilter filter) {
        return userWordDao.count(userWordSearchMapper.toSearch(filter));
    }

    @Override
    public void delete(Collection<DeleteUserWordOptions> userWords) {
        var searches = userWords.stream()
                .map(userWordDomainMapper::toSearch)
                .toList();
        if (searches.isEmpty()) {
            return;
        }
        var search = UserWordSearch.builder()
                .operation(Operations.OR)
                .searches(searches)
                .build();
        var words = userWordDao.findBy(search);
        if (words.isEmpty()) {
            return;
        }
        var userWordIds = words.stream().map(UserWordEntity::getId).toList();

        var deleteSearch = PinnedWordSearch.builder()
                .userWordIds(userWordIds)
                .build();
        pinnedWordDao.delete(deleteSearch);
        userWordDao.delete(words);

    }

}
