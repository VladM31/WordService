package words.com.wordservice.domain.models.pins;

import words.com.wordservice.domain.models.playlist.PinPlayList;

import java.util.Collection;

public record PinOptions(
        Collection<PinPlayList> pins,
        String userId
) {
}
