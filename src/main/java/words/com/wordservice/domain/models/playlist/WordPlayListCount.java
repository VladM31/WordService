package words.com.wordservice.domain.models.playlist;


import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

import java.time.OffsetDateTime;
import java.util.TreeSet;


public record WordPlayListCount(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        Long count,
        TreeSet<String> tags,
        TreeSet<CEFR> cefrs,
        Language language,
        Language translateLanguage
) {
}
