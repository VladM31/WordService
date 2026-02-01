package words.com.wordservice.db.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.TreeSet;

@Converter
public class StringTreeSetConverter implements AttributeConverter<TreeSet<String>, String> {
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(TreeSet<String> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return "[]";
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert TreeSet<String> to String", e);
        }
    }

    @Override
    public TreeSet<String> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData) || "[]".equals(dbData)) {
            return new TreeSet<>();
        }
        try {
            return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(TreeSet.class, String.class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String to TreeSet<String>", e);
        }
    }
}
