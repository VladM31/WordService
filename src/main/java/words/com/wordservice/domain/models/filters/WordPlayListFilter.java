package words.com.wordservice.domain.models.filters;

import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;

import java.util.Collection;

public record WordPlayListFilter(
        Collection<String> ids,
        Collection<String> userIds,
        String name,

        WordPlaylistSortField sortField,
        boolean asc,
        int page,
        int size
) {
}
