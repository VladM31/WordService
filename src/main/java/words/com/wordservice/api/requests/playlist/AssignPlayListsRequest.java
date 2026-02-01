package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.NotEmpty;

import java.util.Collection;

public record AssignPlayListsRequest(
        @NotEmpty
        Collection<String> playListIds
) {
}
