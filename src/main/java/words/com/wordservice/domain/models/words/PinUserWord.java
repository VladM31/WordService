package words.com.wordservice.domain.models.words;

public record PinUserWord(
        String userId,
        String wordId,
        String soundFileName,
        String imageFileName
) {
}
