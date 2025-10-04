package words.com.wordservice.domain.models.filters;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;
import words.com.wordservice.domain.models.enums.sortfileds.UserWordSortField;

import java.util.Collection;

@Builder(toBuilder = true)
public record UserWordFilter(
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
        int page,
        int size
) {
}
