package words.com.wordservice.api.responds.playlist;

import java.time.OffsetDateTime;

public record PlayListCountRespond(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        Long count
) {
}