package words.com.wordservice.db.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.TreeSet;

@Converter
public abstract class EnumTreeSetConverter<E extends Enum<E>> implements AttributeConverter<TreeSet<E>, String> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<E> enumClass;

    protected EnumTreeSetConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(TreeSet<E> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return "[]";
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert TreeSet to String", e);
        }
    }

    @Override
    public TreeSet<E> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData) || "[]".equals(dbData)) {
            return new TreeSet<>();
        }
        try {
            return mapper.readValue(
                    dbData,
                    mapper.getTypeFactory().constructCollectionType(TreeSet.class, enumClass)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String to TreeSet", e);
        }
    }
}