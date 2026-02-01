package words.com.wordservice.db.converters;

import jakarta.persistence.Converter;
import words.com.wordservice.domain.models.enums.CEFR;

@Converter
public class CefrTreeSetConverter extends EnumTreeSetConverter<CEFR> {
    protected CefrTreeSetConverter() {
        super(CEFR.class);
    }
}
