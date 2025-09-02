package words.com.wordservice.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.actions.DeleteWordAction;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.domain.models.words.DeleteWordOptions;
import words.com.wordservice.domain.models.words.ModifyWord;
import words.com.wordservice.domain.models.words.Word;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WordDomainMapper {
    private final FileNameMapper fileNameMapper;

    public Word toModel(WordEntity entity) {
        return toModel(entity, null, null);
    }

    public Word toModel(WordEntity entity, @Nullable String customSoundFileName, @Nullable String customImageFileName) {
        var imageFileName = StringUtils.hasText(customImageFileName) ? customImageFileName : entity.getImageFileName();
        var soundFileName = StringUtils.hasText(customSoundFileName) ? customSoundFileName : entity.getSoundFileName();
        return new Word(
                entity.getId(),
                entity.getOriginal(),
                entity.getLang(),
                entity.getTranslate(),
                entity.getTranslateLang(),
                entity.getCategory(),
                fileNameMapper.toLink(soundFileName),
                fileNameMapper.toLink(imageFileName),
                entity.getType(),
                entity.getCefr(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }

    public WordEntity toEntity(ModifyWord model) {
        return new WordEntity(
                StringUtils.hasText(model.id()) ? model.id() : UUID.randomUUID().toString(),
                model.original(),
                model.lang(),
                model.translate(),
                model.translateLang(),
                model.category(),
                model.soundFileName(),
                model.imageFileName(),
                model.type(),
                model.cefr(),
                model.description(),
                OffsetDateTime.now()
        );
    }

    public Collection<DeleteWordAction> toActions(DeleteWordOptions options) {
        return options.ids().stream()
                .map(id -> new DeleteWordAction(id, options.type()))
                .collect(Collectors.toSet());
    }
}
