package words.com.wordservice.domain.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.entities.history.LearningHistoryEntity;
import words.com.wordservice.db.searches.history.CountLearningHistorySearch;
import words.com.wordservice.db.searches.history.LearningHistorySearch;
import words.com.wordservice.db.searches.history.StatisticsLearningHistorySearch;
import words.com.wordservice.domain.models.filters.history.CountLearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.LearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.StatisticsLearningHistoryFilter;

@Component
public class LearningHistorySearchMapper {
    private final ObjectMapper modelMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public LearningHistorySearch toSearch(LearningHistoryFilter filter) {
        return new LearningHistorySearch(
                filter.userIds(),
                filter.wordIds(),
                filter.date()
        );
    }

    public Pageable toPageable(LearningHistoryFilter filter) {
        Sort dateSort = Sort.sort(LearningHistoryEntity.class).by(LearningHistoryEntity::getDate).descending();
        Sort timeSort = Sort.sort(LearningHistoryEntity.class).by(LearningHistoryEntity::getTime).descending();
        return PageRequest.of(
                filter.page(),
                filter.size(),
                dateSort.and(timeSort)
        );
    }

    public StatisticsLearningHistorySearch toSearch(StatisticsLearningHistoryFilter filter) {
        return modelMapper.convertValue(filter, StatisticsLearningHistorySearch.class);
    }

    public CountLearningHistorySearch toSearch(CountLearningHistoryFilter filter) {
        return modelMapper.convertValue(filter, CountLearningHistorySearch.class);
    }
}
