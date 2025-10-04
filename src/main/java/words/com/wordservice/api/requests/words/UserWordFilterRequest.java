package words.com.wordservice.api.requests.words;

import jakarta.validation.constraints.Min;
import lombok.Singular;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;
import words.com.wordservice.domain.models.enums.sortfileds.UserWordSortField;

import java.util.Collection;

public record UserWordFilterRequest(
        @Singular(ignoreNullCollections = true)
        Collection<String> userWordIds,
        @Singular(ignoreNullCollections = true)
        Collection<String> wordIds,
        @Singular(ignoreNullCollections = true)
        Collection<String> userIds,
        @Singular(ignoreNullCollections = true)
        Collection<Language> languages,
        @Singular(ignoreNullCollections = true)
        Collection<Language> translateLanguages,
        @Singular(ignoreNullCollections = true)
        Collection<String> categories,
        @Singular(ignoreNullCollections = true)
        Collection<CEFR> cefrs,
        @Singular(ignoreNullCollections = true)
        Collection<WordType> types,

        String translate,
        String original,

        UserWordSortField sortField,
        boolean asc,
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        int page,
        @Min(value = 1, message = "Size must be greater than or equal to 1")
        int size
) {
}
