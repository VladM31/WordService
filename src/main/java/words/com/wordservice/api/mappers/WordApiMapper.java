package words.com.wordservice.api.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.words.CreateWordRequest;
import words.com.wordservice.api.requests.words.DeleteWordRequest;
import words.com.wordservice.api.requests.words.WordGetRequest;
import words.com.wordservice.api.responds.WordRespond;
import words.com.wordservice.api.utils.DecodeUtils;
import words.com.wordservice.domain.models.enums.WordType;
import words.com.wordservice.domain.models.filters.WordFilter;
import words.com.wordservice.domain.models.words.DeleteWordOptions;
import words.com.wordservice.domain.models.words.ModifyWord;
import words.com.wordservice.domain.models.words.Word;

import java.util.Collections;
import java.util.UUID;


@Component
public class WordApiMapper {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public WordFilter toModel(User user, WordGetRequest request) {
        var builder = request.toBuilder();

        if (user != null && !user.role().equals(Role.ADMINISTRATION) && request.userId() != null){
            var previousUserId = request.userId();
            builder.userId(new WordGetRequest.UserId(user.id(), previousUserId.isIn()));
        }
        if (user == null || !user.role().equals(Role.ADMINISTRATION)){
            builder.types(Collections.singleton(WordType.PUBLIC));
        }
        DecodeUtils.decode(builder::original, request::original);
        DecodeUtils.decode(builder::translate, request::translate);
        DecodeUtils.decodeCollection(builder::categories, request::categories);

        return objectMapper.convertValue(builder.build(), WordFilter.class);
    }



    public ModifyWord toModifyWord(CreateWordRequest word, User user) {
        return new ModifyWord(
                UUID.randomUUID().toString(),
                word.original(),
                word.lang(),
                word.translate(),
                word.translateLang(),
                word.category(),
                word.soundFileName(),
                word.imageFileName(),
                user.role() == Role.ADMINISTRATION ? WordType.PUBLIC : WordType.CUSTOM,
                word.description(),
                word.cefr()
        );
    }

    public WordRespond toRespond(Word model) {
        return objectMapper.convertValue(model, WordRespond.class);
    }

    public DeleteWordOptions toOptions(DeleteWordRequest request){
        return objectMapper.convertValue(request, DeleteWordOptions.class);
    }
}
