package words.com.wordservice.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import words.com.wordservice.domain.models.enums.CEFR;

@Converter(autoApply = false)
public class CEFRConverter implements AttributeConverter<CEFR, String> {

    @Override
    public String convertToDatabaseColumn(CEFR attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public CEFR convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return CEFR.valueOf(dbData);
    }
}