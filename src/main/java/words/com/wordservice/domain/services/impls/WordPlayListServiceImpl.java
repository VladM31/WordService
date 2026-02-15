package words.com.wordservice.domain.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.daos.LearningHistoryDao;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.daos.UserWordDao;
import words.com.wordservice.db.daos.WordPlayListDao;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.searches.PinnedWordSearch;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.db.searches.WordPlayListSearch;
import words.com.wordservice.domain.mappers.UserWordDomainMapper;
import words.com.wordservice.domain.mappers.WordPlayListDomainMapper;
import words.com.wordservice.domain.mappers.WordPlayListSearchMapper;
import words.com.wordservice.domain.models.enums.PlayListVisibility;
import words.com.wordservice.domain.models.filters.WordPlayListCountFilter;
import words.com.wordservice.domain.models.filters.WordPlayListFilter;
import words.com.wordservice.domain.models.playlist.*;
import words.com.wordservice.domain.services.WordPlayListService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class WordPlayListServiceImpl implements WordPlayListService {
    private final WordPlayListSearchMapper searchMapper;
    private final WordPlayListDao dao;
    private final PinnedWordDao pinnedWordDao;
    private final WordPlayListDomainMapper wordPlayListDomainMapper;
    private final LearningHistoryDao learningHistoryDao;
    private final UserWordDao userWordDao;
    private final UserWordDomainMapper userWordDomainMapper;

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
    @Transactional
    public void assignPlaylists(Collection<String> playListIds, String userId) {
        if (CollectionUtils.isEmpty(playListIds)) {
            throw new RuntimeException("playListIds is empty");
        }
        var search = WordPlayListSearch.builder()
                .ids(playListIds)
                .visibility(PlayListVisibility.PUBLIC)
                .build();
        var entities = dao.findBy(search);
        if (entities.isEmpty()) {
            throw new RuntimeException("No playlists found to assign");
        }
        var newPlayLists = entities.stream()
                .map(it -> wordPlayListDomainMapper.toAssignEntity(it, userId))
                .toList();

        var publicPlayListIds = entities.stream().map(WordPlayListEntity::getId).collect(Collectors.toSet());
        var pinnedWords = pinnedWordDao.findBy(PinnedWordSearch.builder().playListIds(publicPlayListIds).build());
        var wordIds = pinnedWords.stream()
                .map(it -> it.getWord().getWord().getId())
                .collect(Collectors.toSet());


        var userWordSearch = UserWordSearch.builder().userId(userId).wordIds(wordIds).build();
        var savedUserWords = userWordDao.findBy(userWordSearch);

        var unqWordIds = new HashSet<String>();
        var userWordByWordId = savedUserWords.stream()
                .filter(it -> unqWordIds.add(it.getWord().getId()))
                .collect(Collectors.toMap(it -> it.getWord().getId(), it -> it));

        var newPlatListByBaseId = newPlayLists.stream()
                .collect(Collectors.toMap(WordPlayListEntity::getBaseId, it -> it));

        var newPinnedWords = new ArrayList<PinnedWordEntity>();
        var newUserWords = new ArrayList<UserWordEntity>();
        for (var pin : pinnedWords) {
            var playListEntity = newPlatListByBaseId.get(pin.getId().getPlayListId());
            if (playListEntity == null) {
                continue;
            }
            var userWordEntity = userWordByWordId.get(pin.getWord().getWord().getId());
            if (userWordEntity == null) {
                userWordEntity = wordPlayListDomainMapper.toUserWordEntity(userId, pin.getWord().getWord());
                newUserWords.add(userWordEntity);
                userWordByWordId.put(userWordEntity.getWord().getId(), userWordEntity);
            }
            var newPin = wordPlayListDomainMapper.toPinnedWordEntity(playListEntity.getId(), pin, userWordEntity);
            newPinnedWords.add(newPin);
        }

        var historyEntities = newUserWords.stream()
                .map(userWordDomainMapper::toLearningHistoryEntity)
                .toList();

        dao.saveAll(newPlayLists);
        userWordDao.saveAll(newUserWords);
        pinnedWordDao.saveAll(newPinnedWords);
        learningHistoryDao.saveAll(historyEntities);
    }

    @Override
    public Set<AssignedPlaylist> getAssignedPlaylists(String userId) {
        return dao.getAssignedPlaylistIds(userId)
                .stream()
                .map(AssignedPlaylist::new)
                .collect(Collectors.toSet());
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
