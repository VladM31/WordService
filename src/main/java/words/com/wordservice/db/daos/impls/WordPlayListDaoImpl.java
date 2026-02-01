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
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
class WordPlayListDaoImpl implements WordPlayListDao {
    private final WordPlayListRepository repository;
    private final PinnedWordRepository pinnedWordRepository;
    private final UserWordRepository userWordRepository;

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
                CollectionUtils.isEmpty(search.getCefrs()),
                search.getCefrs() != null ? search.getCefrs().stream().map(Enum::name).toList() : List.of(),
                CollectionUtils.isEmpty(search.getTags()),
                search.getTags() != null ? search.getTags() : Set.of(),
                search.getLanguage() != null ? search.getLanguage().name() : null,
                search.getTranslateLanguage() != null ? search.getTranslateLanguage().name() : null,
                search.getVisibility() != null ? search.getVisibility().name() : null,
                search.getAssociationId(),
                CollectionUtils.isEmpty(search.getNotInIds()),
                search.getNotInIds() != null ? search.getNotInIds() : List.of(),
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
                CollectionUtils.isEmpty(search.getCefrs()),
                search.getCefrs() != null ? search.getCefrs().stream().map(Enum::name).toList() : List.of(),
                CollectionUtils.isEmpty(search.getTags()),
                search.getTags() != null ? search.getTags() : Set.of(),
                search.getLanguage() != null ? search.getLanguage().name() : null,
                search.getTranslateLanguage() != null ? search.getTranslateLanguage().name() : null,
                search.getVisibility() != null ? search.getVisibility().name() : null,
                search.getAssociationId(),
                CollectionUtils.isEmpty(search.getNotInIds()),
                search.getNotInIds() != null ? search.getNotInIds() : List.of(),
                Pageable.unpaged()
        ).getContent();
    }

    @Override
    public Set<String> getAssignedPlaylistIds(String userId) {
        return Set.of();
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
            if (userWordRepository.updateGrade(action.value(), lastReadDate, action.userWordId(), action.userId()) > 0){
                pinnedWordRepository.updateGrade(action.value(), lastReadDate, action.userWordId());
            }
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
