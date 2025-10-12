package words.com.wordservice.domain.models.filters;

import lombok.Builder;
import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;
import words.com.wordservice.utils.Range;

import java.util.Collection;

@Builder(toBuilder = true)
public record WordPlayListCountFilter(
        Collection<String> ids,
        Collection<String> userIds,
        String name,
        Range<Long> count,

        WordPlaylistSortField sortField,
        boolean asc,
        int page,
        int size
) {
}
