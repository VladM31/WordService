package words.com.wordservice.db.actions;

import java.util.Collection;

public record DeleteWordDeletePlayListsAction(
        String userId,
        Collection<String> ids
) {
}
