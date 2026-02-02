package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record AssignPlayListsRequest(
        @NotEmpty(message = "Select at least one playlist")
        Set<String> playListIds
) {
}
