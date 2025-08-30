package words.com.wordservice.db.actions;

import lombok.Builder;

@Builder
public record UpdateUserWordGradeAction(
        String userWordId,
        String userId,
        long value
) {
}
