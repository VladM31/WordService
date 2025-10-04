package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayListCreateRequest(
        @NotNull(message = "Play list id is null")
        @NotBlank(message = "Play list name is blank")
        @Size(min = 1, max = 255, message = "Play list name length must be between 1 and 255 characters")
        String name
) {
}