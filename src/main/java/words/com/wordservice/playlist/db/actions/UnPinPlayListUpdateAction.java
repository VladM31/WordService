package words.com.wordservice.playlist.db.actions;

public record UnPinPlayListUpdateAction(
        String userId,
        String playListId
) implements PlayListUpdateAction {
}
