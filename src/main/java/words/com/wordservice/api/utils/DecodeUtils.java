package words.com.wordservice.api.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DecodeUtils {

    public static void decode(Consumer<String> setter, Supplier<String> getter) {
        if (StringUtils.hasText(getter.get())) {
            setter.accept(URLDecoder.decode(getter.get(), StandardCharsets.UTF_8));
        }
    }

    public static void decodeCollection(Consumer<Collection<String>> setter, Supplier<Collection<String>> getter) {
        if (CollectionUtils.isEmpty(getter.get())) {
            return;
        }
        var listValues = new ArrayList<>(getter.get());
        listValues.replaceAll(s -> URLDecoder.decode(s, StandardCharsets.UTF_8));
        setter.accept(listValues);
    }
}
