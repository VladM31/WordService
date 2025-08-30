package words.com.wordservice.db.searches.history;

import lombok.*;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountLearningHistorySearch {
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
}
