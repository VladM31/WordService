package words.com.wordservice.db.searches;

import lombok.*;
import words.com.wordservice.utils.Range;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordPlayListCountSearch {
    @Singular(ignoreNullCollections = true)
    private Collection<String> ids;
    @Singular(ignoreNullCollections = true)
    private Collection<String> userIds;
    private String name;
    private Range<Long> count;
}