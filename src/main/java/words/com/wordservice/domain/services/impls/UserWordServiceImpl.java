package words.com.wordservice.domain.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import words.com.wordservice.db.actions.UserWordUpsertAction;
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
import words.com.wordservice.domain.models.enums.WordType;
import words.com.wordservice.domain.models.filters.UserWordFilter;
import words.com.wordservice.domain.models.words.*;
import words.com.wordservice.domain.services.UserWordService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    public void save(Collection<UserWordCreateDto> userWords) {
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
    @Transactional
    public List<UserWord> savePins(Collection<PinUserWord> userWords) {
        var actions = userWords.stream()
                .map(userWordDomainMapper::toAction)
                .toList();
        userWordDao.upsertAll(actions);

        var historyEntities = userWords.stream()
                .map(userWordDomainMapper::toLearningHistoryEntity)
                .toList();
        learningHistoryDao.saveAll(historyEntities);
        var ids = actions.stream().map(UserWordUpsertAction::id).toList();
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        var filter = UserWordFilter.builder().page(0).size(ids.size()).userWordIds(ids).build();
        return findBy(filter).getContent();
    }

    @Override
    @Transactional
    public void update(UserWordEditDto userWord) {
        var search = UserWordSearch.builder()
                .userWordId(userWord.id())
                .userId(userWord.userId())
                .build();
        var userWordEntity = userWordDao.findBy(search)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User word not found"));
        var userWordUpdateAction = userWordDomainMapper.toUserWordUpdateAction(userWord);
        userWordDao.update(userWordUpdateAction);
        if (userWordEntity.getWord().getType() == WordType.PUBLIC) {
            return;
        }
        var wordUpdateAction = userWordDomainMapper.toWordUpdateAction(userWord, userWordEntity.getWord().getId());
        wordDao.update(wordUpdateAction);
    }

    @Override
    public void addGrades(Collection<UserWordGrade> grades) {
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
    public void delete(Collection<UserWordDeleteDto> userWords) {
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
