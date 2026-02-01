package words.com.wordservice.db.searches;

import lombok.*;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.PlayListVisibility;
import words.com.wordservice.utils.Range;

import java.util.Collection;
import java.util.Set;

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
    @Singular(ignoreNullCollections = true)
    private Set<CEFR> cefrs;
    @Singular(ignoreNullCollections = true)
    private Set<String> tags;
    private Language language;
    private Language translateLanguage;
    private PlayListVisibility visibility;
}