package words.com.wordservice.domain.models.filters.history;

import java.util.Collection;

public record CountLearningHistoryFilter(
        Collection<String> userIds
) {
}
