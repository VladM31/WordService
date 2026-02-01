package words.com.wordservice.api.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.playlist.*;
import words.com.wordservice.api.responds.playlist.PlayListCountRespond;
import words.com.wordservice.api.responds.playlist.PlayListRespond;
import words.com.wordservice.api.utils.DecodeUtils;
import words.com.wordservice.domain.models.enums.PlayListVisibility;
import words.com.wordservice.domain.models.filters.WordPlayListCountFilter;
import words.com.wordservice.domain.models.filters.WordPlayListFilter;
import words.com.wordservice.domain.models.playlist.ModifyPlayList;
import words.com.wordservice.domain.models.playlist.PlayListGrade;
import words.com.wordservice.domain.models.playlist.WordPlayList;
import words.com.wordservice.domain.models.playlist.WordPlayListCount;

import java.util.Collections;
import java.util.UUID;

@Component
public class PlayListApiMapper {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public WordPlayListCountFilter toPublicFilter(PublicPlayListCountGetRequest getRequest) {

        var builder = objectMapper.convertValue(getRequest, WordPlayListCountFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);


        return builder
                .userId("system")
                .visibility(PlayListVisibility.PUBLIC)
                .build();
    }

    public WordPlayListCountFilter toCountFilter(@NonNull User user, PlayListCountGetRequest getRequest) {
        if (user.role().equals(Role.CUSTOMER)) {
            getRequest = getRequest.withUserIds(Collections.singleton(user.id()));
        }
        var builder = objectMapper.convertValue(getRequest, WordPlayListCountFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);

        return builder.build();
    }

    public WordPlayListFilter toFilter(@NonNull User user, PlayListGetRequest getRequest) {
        if (!Role.ADMINISTRATION.equals(user.role())) {
            getRequest = getRequest.withUserIds(Collections.singleton(user.id()));
        }
        var builder = objectMapper.convertValue(getRequest, WordPlayListFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);

        return builder.build();
    }

    public PlayListCountRespond toRespond(WordPlayListCount model) {
        return objectMapper.convertValue(model, PlayListCountRespond.class);
    }

    public PlayListRespond toRespond(WordPlayList model) {
        return objectMapper.convertValue(model, PlayListRespond.class);
    }

    public ModifyPlayList toModifyPlayList(@NonNull User user, @NonNull UpdatePlayListRequest request) {
        return new ModifyPlayList(
                request.id(),
                user.id(),
                request.name()
        );
    }

    public ModifyPlayList toModifyPlayList(@NonNull User user, PlayListCreateRequest request) {
        return new ModifyPlayList(
                UUID.randomUUID().toString(),
                user.id(),
                request.name()
        );
    }

    public PlayListGrade toPlayListGrade(@NonNull User user, PlayListGradeRequest request) {
        return new PlayListGrade(
                request.wordId(),
                user.id(),
                request.wordGrade()
        );
    }
}
