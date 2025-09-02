package words.com.wordservice.domain.models.playlist;

import java.util.List;

public record DeletePlayList(
        String userId,
        List<String> ids
) {
}
