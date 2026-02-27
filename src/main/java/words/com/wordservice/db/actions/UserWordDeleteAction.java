package words.com.wordservice.db.actions;

public record UserWordDeleteAction(
        String id,
        String wordId,
        String userId
) {
}