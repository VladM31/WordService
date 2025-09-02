package words.com.wordservice.domain.mappers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileNameMapper {
    private final String baseFilePath;

    public FileNameMapper(@Value("${app.props.file-path}") String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public String toLink(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return null;
        }
        return baseFilePath + "/" + fileName;
    }
}
