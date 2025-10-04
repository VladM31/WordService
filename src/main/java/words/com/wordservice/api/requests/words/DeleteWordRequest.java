package words.com.wordservice.api.requests.words;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import words.com.wordservice.domain.models.enums.WordType;

import java.util.Set;

public record DeleteWordRequest(
        @NotEmpty(message = "Ids must be specified")
        Set<String> ids,
        @NotNull(message = "Type must be specified")
        WordType type
) {
}
