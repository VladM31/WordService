package words.com.wordservice.playlist.domain.usercase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.daos.PlayListDao;
import words.com.wordservice.playlist.db.actions.PinPlayListUpdateAction;
import words.com.wordservice.playlist.domain.exceptions.PinPlayListException;
import words.com.wordservice.playlist.domain.models.PinPlayListDto;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class PinPlayListUseCase {
    private final PlayListDao playListDao;

    public void execute(PinPlayListDto dto) {
        var action = new PinPlayListUpdateAction(
                dto.userId(),
                dto.playListId(),
                OffsetDateTime.now()
        );
        if (playListDao.update(action) == 0) {
            throw new PinPlayListException("Failed to pin playlist");
        }
    }
}
