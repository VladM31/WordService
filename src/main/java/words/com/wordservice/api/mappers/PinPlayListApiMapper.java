package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.pins.PinPlayRequest;
import words.com.wordservice.domain.models.pins.PinOptions;
import words.com.wordservice.domain.models.pins.PinnedWordId;
import words.com.wordservice.domain.models.pins.UnpinOptions;
import words.com.wordservice.domain.models.playlist.PinPlayList;

import java.time.OffsetDateTime;
import java.util.Collection;

@Component
public class PinPlayListApiMapper {

    public PinOptions toPinOptions(User user, Collection<PinPlayRequest> requests) {
        return new PinOptions(
                requests.stream().map(this::toPinPlayList).toList(),
                user.id()
        );
    }

    public UnpinOptions toUnpinOptions(User user, Collection<PinPlayRequest> requests) {
        return new UnpinOptions(
                requests.stream().map(this::toPinnedWordId).toList(),
                user.id()
        );
    }

    private PinPlayList toPinPlayList(PinPlayRequest request) {
        return new PinPlayList(
                request.playListId(),
                request.wordId(),
                0L,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    private PinnedWordId toPinnedWordId(PinPlayRequest request) {
        return new PinnedWordId(
                request.playListId(),
                request.wordId()
        );
    }
}
