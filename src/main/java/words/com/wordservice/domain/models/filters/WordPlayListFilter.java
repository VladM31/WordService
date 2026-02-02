package words.com.wordservice.domain.models.filters;

import lombok.Builder;
import words.com.wordservice.domain.models.enums.PlayListVisibility;
import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;

import java.util.Collection;

@Builder(toBuilder = true)
public record WordPlayListFilter(
        Collection<String> ids,
        Collection<String> userIds,
        String name,
        PlayListVisibility visibility,

        WordPlaylistSortField sortField,
        boolean asc,
        int page,
        int size
) {
}
