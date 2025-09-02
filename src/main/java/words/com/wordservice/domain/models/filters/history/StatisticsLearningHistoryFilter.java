package words.com.wordservice.domain.models.filters.history;

import words.com.wordservice.utils.Range;

import java.time.OffsetDateTime;
import java.util.Collection;

public record StatisticsLearningHistoryFilter(
        Collection<String> userIds,
        Range<OffsetDateTime> date
) {
}
