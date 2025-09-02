package words.com.wordservice.domain.models.words;

import lombok.Builder;


@Builder(toBuilder = true)
public record ModifyUserWord(
        String id,
        String userId,
        String wordId,

        String soundFileName,
        String imageFileName,
        ModifyWord word
) {

}
