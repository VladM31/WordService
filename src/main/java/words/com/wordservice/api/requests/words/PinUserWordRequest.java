package words.com.wordservice.api.requests.words;

import jakarta.validation.constraints.Pattern;
import org.springframework.lang.Nullable;
import words.com.wordservice.api.valid.annotation.NullableNotBlank;
import words.com.wordservice.api.valid.annotation.NullableSize;

public record PinUserWordRequest(
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Word id is not valid")
        String wordId,
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Custom Sound File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Custom Sound File Name is blank")
        String customSoundFileName,
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Custom Image File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Custom Image File Name is blank")
        String customImageFileName
) {
}