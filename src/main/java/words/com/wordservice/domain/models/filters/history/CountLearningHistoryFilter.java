package words.com.wordservice.domain.models.filters.history;

import lombok.Builder;

import java.util.Collection;

@Builder(toBuilder = true)
public record CountLearningHistoryFilter(
        Collection<String> userIds
) {
}
