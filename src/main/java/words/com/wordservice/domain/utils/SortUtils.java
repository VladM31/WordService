package words.com.wordservice.domain.utils;

import org.springframework.data.domain.Sort;

import java.util.function.Function;

public class SortUtils {

    public static <T> String getSortColumnName(Class<T> entity, Function<T, ?> getField) {
        return Sort.sort(entity).by(getField).stream().iterator().next().getProperty();
    }


}
