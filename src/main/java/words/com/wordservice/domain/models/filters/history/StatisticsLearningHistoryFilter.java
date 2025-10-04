package words.com.wordservice.domain.models.filters.history;

import lombok.Builder;
import words.com.wordservice.utils.Range;

import java.time.OffsetDateTime;
import java.util.Collection;

@Builder(toBuilder = true)
public record StatisticsLearningHistoryFilter(
        Collection<String> userIds,
        Range<OffsetDateTime> date
) {
}
