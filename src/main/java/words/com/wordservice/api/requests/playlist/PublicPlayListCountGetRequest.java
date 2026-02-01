package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import words.com.wordservice.api.requests.utils.LongRange;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;

import java.util.Set;

public record PublicPlayListCountGetRequest(
        Set<String> ids,
        String name,
        LongRange count,
        Set<CEFR> cefrs,
        Set<String> tags,
        Language language,
        Language translateLanguage,

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