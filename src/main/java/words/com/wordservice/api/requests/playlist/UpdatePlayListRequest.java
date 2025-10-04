package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePlayListRequest(
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Play-List id is not valid")
        String id,
        @NotNull(message = "Play list id is null")
        @NotBlank(message = "Play list name is blank")
        @Size(min = 1, max = 255, message = "Play list name length must be between 1 and 255 characters")
        String name
) {
}
