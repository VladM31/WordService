package words.com.wordservice.db.projections;

import java.time.Instant;
import java.time.OffsetDateTime;

public interface WordPlaylistCountProjection {
    String getId();

    String getUserId();

    String getName();

    Instant getCreatedAt();

    Long getCount();
}
