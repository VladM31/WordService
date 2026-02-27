package words.com.wordservice.domain.models.words;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserWordGrade(
        String userWordId,
        String userId,
        long value
) {

}