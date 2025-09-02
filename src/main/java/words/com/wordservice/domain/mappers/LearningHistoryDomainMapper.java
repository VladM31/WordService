package words.com.wordservice.domain.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.entities.history.CountLearningHistoryEntity;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.entities.history.StatisticsLearningHistoryEntity;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.history.CountLearningHistory;
import words.com.wordservice.domain.models.history.LearningHistory;
import words.com.wordservice.domain.models.history.StatisticsLearningHistory;

import java.util.Optional;

@Component
public class LearningHistoryDomainMapper {

    public LearningHistory toModel(LearningHistoryEntity entity, UserWordEntity wordEntity) {
        var optWord = Optional.ofNullable(wordEntity).map(UserWordEntity::getWord);

        return new LearningHistory(
                entity.getId(),
                entity.getUserId(),
                entity.getWordId(),
                optWord.map(WordEntity::getOriginal).orElse("Unknown word"),
                optWord.map(WordEntity::getTranslateLang).orElse(Language.UNDEFINED),
                optWord.map(WordEntity::getLang).orElse(Language.UNDEFINED),
                optWord.map(WordEntity::getCefr).orElse(null),
                entity.getDate(),
                entity.getType(),
                entity.getGrade()
        );
    }

    public StatisticsLearningHistory toStatisticsModel(StatisticsLearningHistoryEntity entity) {
        return new StatisticsLearningHistory(
                entity.getCount(),
                entity.getGrades(),
                entity.getType(),
                entity.getDate()
        );
    }

    public CountLearningHistory toModel(CountLearningHistoryEntity entity) {
        return new CountLearningHistory(
                entity.getCount(),
                entity.getType()
        );
    }
}
