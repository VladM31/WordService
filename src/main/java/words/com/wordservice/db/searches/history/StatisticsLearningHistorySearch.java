package words.com.wordservice.db.searches.history;

import lombok.*;
import words.com.wordservice.utils.Range;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsLearningHistorySearch {
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
    private Range<LocalDate> date;
}
