package words.com.wordservice.domain.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.domain.models.enums.sortfileds.UserWordSortField;
import words.com.wordservice.domain.models.filters.UserWordFilter;
import words.com.wordservice.domain.utils.SortUtils;

import java.util.Map;
import java.util.Objects;

import static words.com.wordservice.domain.models.enums.sortfileds.UserWordSortField.*;

@Component
public class UserWordSearchMapper {
    private final Map<UserWordSortField, String> sortFieldMap = Map.of(
            ORIGIN, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getOriginal)
            ),
            TRANSLATE, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getTranslate)
            ),
            LANG, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getLang)
            ),
            TRANSLATE_LANG, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getTranslateLang)
            ),
            CATEGORY, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getCategory)
            ),
            CREATED_AT, SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getCreatedAt),
            LAST_READ_DATE, SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getLastReadDate)
    );
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Pageable toPageable(UserWordFilter filter) {
        var direction = filter.asc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        UserWordSortField sortField = Objects.requireNonNullElse(filter.sortField(), UserWordSortField.ORIGIN);
        var sort = Sort.by(direction, sortFieldMap.get(sortField));
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    public UserWordSearch toSearch(UserWordFilter filter) {
        return objectMapper.convertValue(filter, UserWordSearch.class);
    }
}
