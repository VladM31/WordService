package words.com.wordservice.api.requests.words;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.Nullable;
import words.com.wordservice.api.valid.annotation.NullableNotBlank;
import words.com.wordservice.api.valid.annotation.NullableSize;

public record CreateUserWordRequest(
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Custom Sound File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Custom Sound File Name is blank")
        String customSoundFileName,
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Custom Image File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Custom Image File Name is blank")
        String customImageFileName,
        @Valid
        CreateWordRequest word
) {
}