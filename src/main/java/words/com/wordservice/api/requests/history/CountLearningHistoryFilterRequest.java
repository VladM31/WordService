package words.com.wordservice.api.requests.history;

import java.util.Collection;

public record CountLearningHistoryFilterRequest(
        Collection<String> userIds
) {
}
