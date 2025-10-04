package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.requests.plan.LearningPlanRequest;
import words.com.wordservice.api.responds.plan.LearningPlanRespond;
import words.com.wordservice.domain.models.LearningPlan;

import java.time.OffsetDateTime;

@Component
public class LearningPlanApiMapper {

    public LearningPlan toModel(LearningPlanRequest request, String userId) {
        return new LearningPlan(
                userId,
                request.wordsPerDay(),
                request.nativeLang(),
                request.learningLang(),
                request.cefr(),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    public LearningPlanRespond toRespond(LearningPlan model) {
        return new LearningPlanRespond(
                model.wordsPerDay(),
                model.nativeLang(),
                model.learningLang(),
                model.cefr(),
                model.createdAt(),
                model.updatedAt()
        );
    }
}
