package words.com.wordservice.db.daos.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.actions.DeleteUserWordAction;
import words.com.wordservice.db.actions.UpdateUserWordGradeAction;
import words.com.wordservice.db.actions.UserWordUpsertAction;
import words.com.wordservice.db.daos.UserWordDao;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.db.searches.WordSearch;
import words.com.wordservice.domain.models.enums.WordType;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
class UserWordDaoImpl implements UserWordDao {
    private final UserWordRepository repository;
    private final WordRepository wordRepository;

    @Override
    public Page<UserWordEntity> findBy(UserWordSearch s, Pageable pageable) {
        return repository.findAll(s, pageable);
    }

    @Override
    public List<UserWordEntity> findBy(UserWordSearch s) {
        return repository.findAll(s);
    }

    @Override
    public void save(UserWordEntity entity) {
        repository.save(entity);
    }

    @Override
    public void saveAll(Collection<UserWordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    public void update(UserWordEntity entity) {
        repository.save(entity);
    }

    @Override
    public void updateAll(Collection<UserWordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void upsertAll(Collection<UserWordUpsertAction> entities) {
        for (UserWordUpsertAction action : entities) {
            repository.upsert(
                    action.id(),
                    OffsetDateTime.now(),
                    action.customImageFileName(),
                    action.customSoundFileNam(),
                    OffsetDateTime.now(),
                    0,
                    action.userId(),
                    action.wordId()
            );
        }
    }

    @Override
    @Transactional
    public void addGrades(Collection<UpdateUserWordGradeAction> actions) {
        for (UpdateUserWordGradeAction action : actions) {
            repository.updateGrade(
                    action.value(),
                    OffsetDateTime.now(),
                    action.userWordId(),
                    action.userId()
            );
        }
    }

    @Override
    @Transactional
    public void delete(Collection<DeleteUserWordAction> actions) {
        for (DeleteUserWordAction action : actions) {
            var searchBuilder = UserWordSearch.builder()
                    .userWordId(action.id())
                    .wordId(action.wordId());

            if (StringUtils.hasText(action.userId())) {
                searchBuilder.userId(action.userId());
            }
            var search = searchBuilder.build();
            if (repository.delete(search) == 0){
                continue;
            }
            var wordSearch = WordSearch.builder()
                    .wordId(action.wordId())
                    .type(WordType.CUSTOM)
                    .build();
            wordRepository.delete(wordSearch);
        }
    }

    @Override
    public void delete(List<UserWordEntity> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public long count(UserWordSearch s) {
        return repository.count(s);
    }
}
