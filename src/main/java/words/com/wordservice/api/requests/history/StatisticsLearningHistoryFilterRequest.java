package words.com.wordservice.api.requests.history;

import words.com.wordservice.api.requests.utils.OffsetDateTimeRange;

import java.util.Collection;

public record StatisticsLearningHistoryFilterRequest(
        Collection<String> userIds,
        OffsetDateTimeRange date
) {
}