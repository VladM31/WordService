package words.com.wordservice.playlist.db.actions;

import java.time.OffsetDateTime;

public record PinPlayListUpdateAction(
        String userId,
        String playListId,
        OffsetDateTime pinnedAt
) implements PlayListUpdateAction {
}
