package words.com.wordservice.api.requests.history;

import jakarta.validation.constraints.Min;
import words.com.wordservice.api.requests.utils.LocalDateRange;
import words.com.wordservice.utils.Range;

import java.time.LocalDate;
import java.util.Collection;

public record LearningHistoryFilterRequest(
        Collection<String> userIds,
        Collection<String> wordIds,
        LocalDateRange date,
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        int page,
        @Min(value = 1, message = "Size must be greater than or equal to 1")
        int size
) {
}
