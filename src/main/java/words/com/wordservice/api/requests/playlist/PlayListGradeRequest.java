package words.com.wordservice.api.requests.playlist;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record PlayListGradeRequest(
        @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Play-List id is not valid")
        String wordId,
        @Min(value = 0, message = "Word grade must be greater than or equal to 0")
        @Max(value = Integer.MAX_VALUE, message = "Word grade must be less than or equal to {value}")
        int wordGrade
) {
}