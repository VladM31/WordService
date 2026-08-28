package words.com.wordservice.domain.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.searches.WordPlayListCountSearch;
import words.com.wordservice.db.searches.WordPlayListSearch;
import words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField;
import words.com.wordservice.domain.models.filters.WordPlayListCountFilter;
import words.com.wordservice.domain.models.filters.WordPlayListFilter;
import words.com.wordservice.domain.utils.SortUtils;

import java.util.Map;
import java.util.Objects;

import static words.com.wordservice.domain.models.enums.sortfileds.WordPlaylistSortField.*;

@Component
public class WordPlayListSearchMapper {
    private final Map<WordPlaylistSortField, String> sortFieldMap = Map.of(
            NAME, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getName),
            CREATED_AT, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getCreatedAt),
            CEFR, "cefrs",
            LANGUAGE, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getLanguage),
            TRANSLATE_LANGUAGE, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getTranslateLanguage)
    );
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Pageable toPageable(WordPlayListFilter filter) {
        var sort = toSort(filter.sortField(), filter.asc());
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    public Pageable toPageable(WordPlayListCountFilter filter) {
        var sort = toSort(filter.sortField(), filter.asc());
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    private Sort toSort(WordPlaylistSortField field, boolean asc) {
        var sortField = sortFieldMap.get(Objects.requireNonNullElse(field, NAME));
        var direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        var mainOrder = new Sort.Order(direction, sortField);

        var pinnedOrder = new Sort.Order(Sort.Direction.DESC, "pinnedAt", Sort.NullHandling.NULLS_LAST);
        return Sort.by(pinnedOrder, mainOrder);
    }

    public WordPlayListSearch toSearch(WordPlayListFilter filter) {
        return objectMapper.convertValue(filter, WordPlayListSearch.class);
    }

    public WordPlayListCountSearch toSearch(WordPlayListCountFilter filter) {
        return objectMapper.convertValue(filter, WordPlayListCountSearch.class);
    }
}
