package words.com.wordservice.domain.models.words;

public record UserWordDeleteDto(
        String id,
        String userId,
        String wordId
) {
}
