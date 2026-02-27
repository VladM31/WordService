package words.com.wordservice.domain.models.words;

import lombok.Builder;


@Builder(toBuilder = true)
public record UserWordCreateDto(
        String id,
        String userId,

        String soundFileName,
        String imageFileName,
        ModifyWord word
) {

}
