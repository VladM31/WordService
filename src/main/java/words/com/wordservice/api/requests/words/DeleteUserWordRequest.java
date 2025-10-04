package words.com.wordservice.api.requests.words;

import jakarta.validation.constraints.Pattern;

public record DeleteUserWordRequest(
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "User word id is not valid")
        String id,
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Word id is not valid")
        String wordId
) {
}
