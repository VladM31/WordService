package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.With;
import words.com.wordservice.api.requests.utils.LongRange;
import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;

import java.util.Set;

public record PlayListCountGetRequest(
        Set<String> ids,
        @With
        Set<String> userIds,
        String name,
        LongRange count,

        @NotNull(message = "Sort field must be specified")
        WordPlaylistSortField sortField,
        boolean asc,
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        int page,
        @Min(value = 1, message = "Size must be greater than or equal to 1")
        @Max(value = 100, message = "Size must be less than or equal to 100")
        int size
) {
}