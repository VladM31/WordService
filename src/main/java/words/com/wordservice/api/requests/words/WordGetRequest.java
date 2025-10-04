package words.com.wordservice.api.requests.words;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Singular;
import org.springframework.lang.NonNull;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;
import words.com.wordservice.domain.models.enums.sortfileds.WordSortField;

import java.util.Collection;
import java.util.Set;

@Builder(toBuilder = true)
public record WordGetRequest(
        @Singular(ignoreNullCollections = true)
        Collection<String> wordIds,
        @Singular(ignoreNullCollections = true)
        Set<Language> languages,
        @Singular(ignoreNullCollections = true)
        Set<Language> translateLanguages,
        @Singular(ignoreNullCollections = true)
        Collection<String> categories,
        Set<CEFR> cefrs,
        @Singular(ignoreNullCollections = true)
        Set<WordType> types,

        String translate,
        String original,
        UserId userId,

        @NotNull
        WordSortField sortField,
        boolean asc,
        @Min(0)
        int page,
        @Min(1)
        @Max(100)
        int size
) {


    public record UserId(
            @NonNull
            String id,
            boolean isIn
    ) {
    }

}
