package words.com.wordservice.domain.models.playlist;

import lombok.Builder;
import words.com.wordservice.domain.models.words.PinnedWord;

import java.time.OffsetDateTime;
import java.util.List;


@Builder(toBuilder = true)
public record WordPlayList(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        List<PinnedWord> words
) {
}
