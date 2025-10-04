package words.com.wordservice.api.responds.playlist;

import words.com.wordservice.api.responds.UserWordRespond;

import java.time.OffsetDateTime;
import java.util.List;

public record PlayListRespond(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        List<PinnedWordRespond> words
) {

    public record PinnedWordRespond(
            Long learningGrade,
            OffsetDateTime createdAt,
            OffsetDateTime lastReadDate,
            UserWordRespond word
    ) {
    }


}
