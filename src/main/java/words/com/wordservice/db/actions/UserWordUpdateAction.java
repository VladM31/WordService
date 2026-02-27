package words.com.wordservice.db.actions;

import org.springframework.lang.Nullable;

public record UserWordUpdateAction(
        String id,
        String userId,
        @Nullable
        String soundFileName,
        @Nullable
        String imageFileName
) {
}
