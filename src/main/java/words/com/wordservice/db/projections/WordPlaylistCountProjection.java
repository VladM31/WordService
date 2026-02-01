package words.com.wordservice.db.projections;

import words.com.wordservice.domain.models.enums.Language;

import java.time.Instant;

public interface WordPlaylistCountProjection {
    String getId();

    String getUserId();

    String getName();

    Instant getCreatedAt();

    Long getCount();

    String getTags();

    String getCefrs();

    Language getLanguage();

    Language getTranslateLanguage();
}
