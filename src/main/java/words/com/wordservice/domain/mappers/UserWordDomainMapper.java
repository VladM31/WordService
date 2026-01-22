package words.com.wordservice.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.actions.DeleteUserWordAction;
import words.com.wordservice.db.actions.UpdateUserWordGradeAction;
import words.com.wordservice.db.actions.UserWordUpsertAction;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.domain.models.enums.LearningHistoryType;
import words.com.wordservice.domain.models.words.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserWordDomainMapper {
    private final WordDomainMapper wordDomainMapper;

    public UserWord toModel(UserWordEntity entity) {
        return new UserWord(
                entity.getId(),
                entity.getUserId(),
                entity.getLearningGrade(),
                entity.getCreatedAt(),
                entity.getLastReadDate(),
                entity.getWord().getType(),
                wordDomainMapper.toModel(entity.getWord(), entity.getCustomSoundFileName(), entity.getCustomImageFileName())
        );
    }

    public UserWordEntity toEntity(ModifyUserWord model) {
        return new UserWordEntity(
                StringUtils.hasText(model.id()) ? model.id() : UUID.randomUUID().toString(),
                model.userId(),
                0,
                model.soundFileName(),
                model.imageFileName(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                wordDomainMapper.toEntity(model.word())
        );
    }

    public UserWordUpsertAction toAction(PinUserWord model) {
        return new UserWordUpsertAction(
                UUID.randomUUID().toString(),
                model.userId(),
                model.wordId(),
                model.imageFileName(),
                model.soundFileName()
        );
    }

    public UpdateUserWordGradeAction toAction(GradeUserWord model) {
        return new UpdateUserWordGradeAction(
                model.userWordId(),
                model.userId(),
                model.value()
        );
    }

    public DeleteUserWordAction toAction(DeleteUserWordOptions model) {
        return new DeleteUserWordAction(
                model.id(),
                model.wordId(),
                model.userId()
        );
    }

    public UserWordSearch toSearch(DeleteUserWordOptions options) {
        return UserWordSearch.builder()
                .userWordId(options.id())
                .wordId(options.wordId())
                .userId(options.userId())
                .build();
    }

    public LearningHistoryEntity toLearningHistoryEntity(ModifyUserWord model) {
        return new LearningHistoryEntity(
                UUID.randomUUID().toString(),
                model.userId(),
                model.word().id(),
                LocalDate.now(ZoneId.of("UTC")),
                OffsetTime.now(),
                LearningHistoryType.CREATE,
                0
        );
    }

    public LearningHistoryEntity toLearningHistoryEntity(PinUserWord model) {
        return new LearningHistoryEntity(
                UUID.randomUUID().toString(),
                model.userId(),
                model.wordId(),
                LocalDate.now(ZoneId.of("UTC")),
                OffsetTime.now(),
                LearningHistoryType.CREATE,
                0
        );
    }
}
