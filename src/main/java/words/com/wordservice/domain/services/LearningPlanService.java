package words.com.wordservice.domain.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.domain.models.LearningPlan;
import words.com.wordservice.domain.models.filters.LearningPlanFilter;

import java.util.Optional;

public interface LearningPlanService {

    default Optional<LearningPlan> findByUserId(String userId) {
        var filter = LearningPlanFilter.builder().userId(userId).page(0).size(1).build();
        return findBy(filter)
                .stream()
                .findFirst();
    }

    Page<LearningPlan> findBy(LearningPlanFilter filter);

    LearningPlan create(LearningPlan learningPlan);

    void update(LearningPlan learningPlan);
}
