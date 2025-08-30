package words.com.wordservice.db.actions;

import words.com.wordservice.domain.models.enums.WordType;

public record DeleteWordAction(
        String id,
        WordType type
) {
}