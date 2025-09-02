package words.com.wordservice.domain.models.words;


import lombok.Builder;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;

@Builder(toBuilder = true)
public record ModifyWord(
        String id,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        String category,
        String soundFileName,
        String imageFileName,
        WordType type,
        String description,
        CEFR cefr
) {

}
