package words.com.wordservice.db.actions;

import lombok.Builder;

@Builder
public record UserWordGradeUpdateAction(
        String userWordId,
        String userId,
        long value
) {
}
