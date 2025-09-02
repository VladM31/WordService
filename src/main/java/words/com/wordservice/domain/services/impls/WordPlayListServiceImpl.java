package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import words.com.wordservice.db.daos.LearningHistoryDao;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.daos.WordPlayListDao;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.searches.PinnedWordSearch;
import words.com.wordservice.db.searches.WordPlayListSearch;
import words.com.wordservice.domain.mappers.WordPlayListDomainMapper;
import words.com.wordservice.domain.mappers.WordPlayListSearchMapper;
import words.com.wordservice.domain.models.filters.WordPlayListCountFilter;
import words.com.wordservice.domain.models.filters.WordPlayListFilter;
import words.com.wordservice.domain.models.playlist.*;
import words.com.wordservice.domain.services.WordPlayListService;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class WordPlayListServiceImpl implements WordPlayListService {
    private final WordPlayListSearchMapper searchMapper;
    private final WordPlayListDao dao;
    private final PinnedWordDao pinnedWordDao;
    private final WordPlayListDomainMapper wordPlayListDomainMapper;
    private final LearningHistoryDao learningHistoryDao;

    @Override
    public Page<WordPlayList> findBy(WordPlayListFilter filter) {
        var paged = dao.findBy(
                searchMapper.toSearch(filter),
                searchMapper.toPageable(filter)
        );
        if (paged.isEmpty()) {
            return paged.map(wordPlayListDomainMapper::toModel);
        }

        var playListIds = paged.stream().map(WordPlayListEntity::getId).collect(Collectors.toSet());

        var pinSearch = PinnedWordSearch.builder().playListIds(playListIds).build();
        var pinsByPlayList = pinnedWordDao.findBy(pinSearch).stream()
                .collect(Collectors.groupingBy(it -> it.getId().getPlayListId()));

        return paged.map(it -> wordPlayListDomainMapper.toModel(it, pinsByPlayList));
    }

    @Override
    public Page<WordPlayListCount> findBy(WordPlayListCountFilter filter) {
        return dao.findBy(
                searchMapper.toSearch(filter),
                searchMapper.toPageable(filter)
        ).map(wordPlayListDomainMapper::toModel);
    }

    @Override
    public void saveAll(Collection<ModifyPlayList> playLists) {
        var entities = playLists.stream()
                .map(wordPlayListDomainMapper::toEntity)
                .collect(Collectors.toList());
        dao.saveAll(entities);
    }

    @Override
    public void updateAll(Collection<ModifyPlayList> playLists) {
        var entities = playLists.stream()
                .map(wordPlayListDomainMapper::toUpdateEntity)
                .collect(Collectors.toList());
        dao.updateAll(entities);
    }

    @Override
    public void updateGrades(Collection<PlayListGrade> grades) {
        learningHistoryDao.saveAll(
                grades.stream().map(wordPlayListDomainMapper::toEntity).toList()
        );
        dao.updateGrades(
                grades.stream().map(wordPlayListDomainMapper::toAction).toList()
        );
    }

    @Override
    public void delete(DeletePlayList deletePlayList) {
        var search = WordPlayListSearch.builder()
                .userId(deletePlayList.userId())
                .ids(deletePlayList.ids())
                .build();
        dao.delete(search);
    }
}
