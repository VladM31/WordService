package words.com.wordservice.domain.models.filters.history;

import words.com.wordservice.utils.Range;

import java.time.LocalDate;
import java.util.Collection;

public record LearningHistoryFilter(
        Collection<String> userIds,
        Collection<String> wordIds,
        Range<LocalDate> date,
        int page,
        int size
) {
}
