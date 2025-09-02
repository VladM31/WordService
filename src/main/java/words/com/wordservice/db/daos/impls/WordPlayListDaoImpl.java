package words.com.wordservice.db.daos.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.actions.UpdateUserWordGradeAction;
import words.com.wordservice.db.daos.WordPlayListDao;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.projections.WordPlaylistCountProjection;
import words.com.wordservice.db.searches.WordPlayListCountSearch;
import words.com.wordservice.db.searches.WordPlayListSearch;
import words.com.wordservice.utils.Range;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
class WordPlayListDaoImpl implements WordPlayListDao {
    private final WordPlayListRepository repository;

    @Override
    public Page<WordPlayListEntity> findBy(WordPlayListSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    public List<WordPlayListEntity> findBy(WordPlayListSearch search) {
        return repository.findAll(search);
    }

    @Override
    public Page<WordPlaylistCountProjection> findBy(WordPlayListCountSearch search, Pageable pageable) {
        return repository.findPlayListsWithCount(
                CollectionUtils.isEmpty(search.getIds()),
                search.getIds(),
                CollectionUtils.isEmpty(search.getUserIds()),
                search.getUserIds(),
                search.getName(),
                Range.to(search.getCount()),
                Range.from(search.getCount()),
                pageable
        );
    }

    @Override
    public List<WordPlaylistCountProjection> findBy(WordPlayListCountSearch search) {
        return repository.findPlayListsWithCount(
                CollectionUtils.isEmpty(search.getIds()),
                search.getIds(),
                CollectionUtils.isEmpty(search.getUserIds()),
                search.getUserIds(),
                search.getName(),
                Range.to(search.getCount()),
                Range.from(search.getCount()),
                Pageable.unpaged()
        ).getContent();
    }

    @Override
    public void saveAll(Collection<WordPlayListEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    public void updateAll(Collection<WordPlayListEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void updateGrades(Collection<UpdateUserWordGradeAction> actions) {
        OffsetDateTime lastReadDate = OffsetDateTime.now();
        for (UpdateUserWordGradeAction action : actions) {
            repository.updateGrade(action.value(), lastReadDate, action.userWordId(), action.userId());
        }
    }

    @Override
    @Transactional
    public void delete(WordPlayListSearch search) {
        if (CollectionUtils.isEmpty(search.getIds())) {
            return;
        }
        repository.delete(search);
    }
}
