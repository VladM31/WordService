package words.com.wordservice.api.requests.pins;

import jakarta.validation.constraints.Pattern;

public record PinPlayRequest(
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "User word id is not valid")
        String playListId,
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "User word id is not valid")
        String wordId
) {
}
