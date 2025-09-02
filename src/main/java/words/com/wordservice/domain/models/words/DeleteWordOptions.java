package words.com.wordservice.domain.models.words;

import words.com.wordservice.domain.models.enums.WordType;

import java.util.Collection;

public record DeleteWordOptions(
        Collection<String> ids,
        WordType type
) {

    public static DeleteWordOptions ofAdministrator(Collection<String> ids) {
        return new DeleteWordOptions(ids, WordType.PUBLIC);
    }
}
