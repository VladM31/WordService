package words.com.wordservice.domain.models.playlist;


import java.time.OffsetDateTime;


public record WordPlayListCount(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        Long count
) {
}
