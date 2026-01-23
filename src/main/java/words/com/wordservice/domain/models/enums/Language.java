package words.com.wordservice.domain.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    POLISH("pl"),
    ENGLISH("en"),
    GERMAN("de"),
    FRENCH("fr"),
    UKRAINIAN("uk"),
    CZECH("cz"),
    UNDEFINED(null);

    private final String shortName;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Language fromShortName(String shortName) {
        for (Language lang : Language.values()) {
            if (lang.shortName != null && lang.shortName.equalsIgnoreCase(shortName)) {
                return lang;
            }
        }
        return Language.valueOf(shortName);
    }
}
