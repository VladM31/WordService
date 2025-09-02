package words.com.wordservice.domain.services;

import words.com.wordservice.domain.models.pins.PinOptions;
import words.com.wordservice.domain.models.pins.UnpinOptions;

public interface PinPlayListService {

    void pin(PinOptions options);

    void unpin(UnpinOptions options);
}
