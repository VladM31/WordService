package words.com.wordservice.domain.models.pins;

import java.util.Collection;

public record UnpinOptions(
        Collection<PinnedWordId> pins,
        String userId
) {
}
