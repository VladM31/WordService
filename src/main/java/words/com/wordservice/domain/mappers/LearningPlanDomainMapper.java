package words.com.wordservice.domain.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.db.entities.LearningPlanEntity;
import words.com.wordservice.domain.models.LearningPlan;

import java.time.OffsetDateTime;

@Component
public class LearningPlanDomainMapper {

    public LearningPlanEntity toEntity(LearningPlan model) {
        return new LearningPlanEntity(
                model.userId(),
                model.wordsPerDay(),
                model.nativeLang(),
                model.learningLang(),
                model.cefr(),
                model.createdAt(),
                OffsetDateTime.now()
        );
    }

    public LearningPlan toModel(LearningPlanEntity entity) {
        return new LearningPlan(
                entity.getUserId(),
                entity.getWordsPerDay(),
                entity.getNativeLang(),
                entity.getLearningLang(),
                entity.getCefr(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
