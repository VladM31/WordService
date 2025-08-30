package words.com.wordservice.db.projections;

import java.time.OffsetDateTime;

public interface WordPlaylistCountProjection {
    String getId();

    String getUserId();

    String getName();

    OffsetDateTime getCreatedAt();

    Long getCount();
}
