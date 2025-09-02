package words.com.wordservice.domain.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.actions.UpdateUserWordGradeAction;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.projections.WordPlaylistCountProjection;
import words.com.wordservice.domain.models.enums.LearningHistoryType;
import words.com.wordservice.domain.models.playlist.ModifyPlayList;
import words.com.wordservice.domain.models.playlist.PlayListGrade;
import words.com.wordservice.domain.models.playlist.WordPlayList;
import words.com.wordservice.domain.models.playlist.WordPlayListCount;
import words.com.wordservice.domain.models.words.PinnedWord;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class WordPlayListDomainMapper {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public WordPlayList toModel(WordPlayListEntity entity) {
        return toModel(entity, Collections.emptyMap());

    }

    public WordPlayList toModel(WordPlayListEntity entity, Map<String, List<PinnedWordEntity>> pinnedWords) {
        return new WordPlayList(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getCreatedAt(),
                pinnedWords.getOrDefault(entity.getId(), Collections.emptyList()).stream().map(this::toPinnedWordModel).toList()
        );
    }

    public WordPlayListCount toModel(WordPlaylistCountProjection projection) {
        return new WordPlayListCount(
                projection.getId(),
                projection.getUserId(),
                projection.getName(),
                projection.getCreatedAt(),
                projection.getCount()
        );
    }

    private PinnedWord toPinnedWordModel(PinnedWordEntity entity) {
        return objectMapper.convertValue(entity, PinnedWord.class);
    }

    public WordPlayListEntity toEntity(ModifyPlayList model) {
        return new WordPlayListEntity(
                model.id(),
                model.userId(),
                model.name(),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    public WordPlayListEntity toUpdateEntity(ModifyPlayList model) {
        return new WordPlayListEntity(
                model.id(),
                model.userId(),
                model.name(),
                null,
                OffsetDateTime.now()
        );
    }

    public UpdateUserWordGradeAction toAction(PlayListGrade model) {
        return objectMapper.convertValue(model, UpdateUserWordGradeAction.class);
    }

    public LearningHistoryEntity toEntity(PlayListGrade model) {
        return new LearningHistoryEntity(
                UUID.randomUUID().toString(),
                model.userId(),
                model.wordId(),
                LocalDate.now(ZoneId.of("UTC")),
                OffsetTime.now(),
                LearningHistoryType.UPDATE,
                model.wordGrade()
        );
    }
}
