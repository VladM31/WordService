package words.com.wordservice.domain.models.filters.history;

import lombok.Builder;
import words.com.wordservice.utils.Range;

import java.time.LocalDate;
import java.util.Collection;

@Builder(toBuilder = true)
public record LearningHistoryFilter(
        Collection<String> userIds,
        Collection<String> wordIds,
        Range<LocalDate> date,
        int page,
        int size
) {
}
