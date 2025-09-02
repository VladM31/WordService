package words.com.wordservice.domain.mappers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.searches.LearningPlanSearch;
import words.com.wordservice.domain.models.filters.LearningPlanFilter;

@Component
public class LearningPlanSearchMapper {

    public LearningPlanSearch toSearch(LearningPlanFilter filter) {
        return new LearningPlanSearch(filter.userIds());
    }

    public Pageable toPageable(LearningPlanFilter filter) {
        return Pageable.ofSize(filter.size()).withPage(filter.page());
    }
}
