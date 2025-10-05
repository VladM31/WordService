package words.com.wordservice.api.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.words.CreateUserWordRequest;
import words.com.wordservice.api.requests.words.DeleteUserWordRequest;
import words.com.wordservice.api.requests.words.PinUserWordRequest;
import words.com.wordservice.api.requests.words.UserWordFilterRequest;
import words.com.wordservice.api.responds.UserWordRespond;
import words.com.wordservice.api.utils.DecodeUtils;
import words.com.wordservice.domain.models.filters.UserWordFilter;
import words.com.wordservice.domain.models.words.DeleteUserWordOptions;
import words.com.wordservice.domain.models.words.ModifyUserWord;
import words.com.wordservice.domain.models.words.PinUserWord;
import words.com.wordservice.domain.models.words.UserWord;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserWordApiMapper {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final WordApiMapper wordApiMapper;

    public UserWordFilter toFilter(User user, UserWordFilterRequest getRequest) {
        var filterBuilder = objectMapper.convertValue(getRequest, UserWordFilter.class).toBuilder();
        DecodeUtils.decode(filterBuilder::original, getRequest::original);
        DecodeUtils.decode(filterBuilder::translate, getRequest::translate);
        DecodeUtils.decodeCollection(filterBuilder::categories, getRequest::categories);

        if (Role.ADMINISTRATION != user.role()){
            filterBuilder.userIds(Collections.singleton(user.id()));
        }

        return filterBuilder.build();
    }

    public UserWordRespond toRespond(UserWord model) {
        return objectMapper.convertValue(model, UserWordRespond.class);
    }

    public ModifyUserWord toModel(User user, CreateUserWordRequest request){
        return new ModifyUserWord(
                UUID.randomUUID().toString(),
                user.id(),
                request.customSoundFileName(),
                request.customImageFileName(),
                wordApiMapper.toModifyWord(request.word(),user)
        );
    }

    public PinUserWord toModel(User user, PinUserWordRequest request){
        return new PinUserWord(
                user.id(),
                request.wordId(),
                request.customSoundFileName(),
                request.customImageFileName()
        );
    }



    public DeleteUserWordOptions toOptions(DeleteUserWordRequest request, String userId) {
        return new DeleteUserWordOptions(
                request.id(),
                userId,
                request.wordId()
        );
    }
}
