package words.com.wordservice.db.actions;


public record UserWordUpsertAction(
        String id,
        String userId,
        String wordId,
        String customImageFileName,
        String customSoundFileNam
) {
}
