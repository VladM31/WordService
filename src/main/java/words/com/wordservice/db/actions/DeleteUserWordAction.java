package words.com.wordservice.db.actions;

public record DeleteUserWordAction(
        String id,
        String wordId,
        String userId
) {
}