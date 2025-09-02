package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import words.com.wordservice.db.daos.LearningHistoryDao;
import words.com.wordservice.db.daos.UserWordDao;
import words.com.wordservice.domain.mappers.UserWordDomainMapper;
import words.com.wordservice.domain.mappers.UserWordSearchMapper;
import words.com.wordservice.domain.models.filters.UserWordFilter;
import words.com.wordservice.domain.models.words.*;
import words.com.wordservice.domain.services.UserWordService;

import java.util.Collection;

@RequiredArgsConstructor
class UserWordServiceImpl implements UserWordService {
    private final UserWordDao userWordDao;
    private final UserWordDomainMapper userWordDomainMapper;
    private final UserWordSearchMapper userWordSearchMapper;
    private final LearningHistoryDao learningHistoryDao;

    @Override
    public Page<UserWord> findBy(UserWordFilter filter) {
        var search = userWordSearchMapper.toSearch(filter);
        var pageable = userWordSearchMapper.toPageable(filter);
        return userWordDao.findBy(search, pageable)
                .map(userWordDomainMapper::toModel);
    }

    @Override
    public void save(Collection<ModifyUserWord> userWords) {
        var entities = userWords.stream()
                .map(userWordDomainMapper::toEntity)
                .toList();
        userWordDao.saveAll(entities);

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
        var actions = userWords.stream()
                .map(userWordDomainMapper::toAction)
                .toList();
        userWordDao.delete(actions);
    }
}
