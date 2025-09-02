package words.com.wordservice.domain.models.words;

public record DeleteUserWordOptions(
        String id,
        String userId,
        String wordId
) {
}
