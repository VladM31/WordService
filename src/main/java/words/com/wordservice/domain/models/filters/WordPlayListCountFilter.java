package words.com.wordservice.domain.models.filters;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.PlayListVisibility;
import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;
import words.com.wordservice.utils.Range;

import java.util.Collection;
import java.util.Set;

@Builder(toBuilder = true)
public record WordPlayListCountFilter(
        @Singular(ignoreNullCollections = true)
        Collection<String> ids,
        @Singular(ignoreNullCollections = true)
        Collection<String> userIds,
        @Singular(ignoreNullCollections = true)
        Collection<String> notInIds,
        @Singular(ignoreNullCollections = true)
        Set<CEFR> cefrs,
        @Singular(ignoreNullCollections = true)
        Set<String> tags,
        Language language,
        Language translateLanguage,
        String name,
        Range<Long> count,
        PlayListVisibility visibility,
        String associationId,

        WordPlaylistSortField sortField,
        boolean asc,
        int page,
        int size
) {
}
