package words.com.wordservice.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.daos.UserWordDao;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.domain.models.pins.PinOptions;
import words.com.wordservice.domain.models.pins.PinnedWordId;
import words.com.wordservice.domain.models.playlist.PinPlayList;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PinPlayListDomainMapper {
    private final UserWordDao userWordDao;

    public PinnedWordEntity.PinnedWordId toEntity(PinnedWordId id) {
        return new PinnedWordEntity.PinnedWordId(id.playListId(), id.userWordId());
    }

    public List<PinnedWordEntity> toEntities(PinOptions options) {
        if (CollectionUtils.isEmpty(options.pins())) {
            return Collections.emptyList();
        }
        var userWordIds = options.pins().stream().map(PinPlayList::wordId).toList();
        var search = UserWordSearch.builder().userWordIds(userWordIds).userId(options.userId()).build();
        var entities = userWordDao.findBy(search);
        var userWordById = entities.stream().collect(Collectors.toMap(UserWordEntity::getId, Function.identity()));

        return options.pins().stream()
                .map(pin -> toEntity(pin, userWordById))
                .filter(it -> it.getWord() != null)
                .toList();
    }

    private PinnedWordEntity toEntity(PinPlayList pin, Map<String, UserWordEntity> userWordById) {
        var id = new PinnedWordEntity.PinnedWordId(pin.playListId(), pin.wordId());

        return new PinnedWordEntity(
                id,
                pin.learningGrade(),
                pin.createdAt(),
                OffsetDateTime.now(),
                userWordById.get(pin.wordId())
        );
    }
}
