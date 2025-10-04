package words.com.wordservice.api.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.history.CountLearningHistoryFilterRequest;
import words.com.wordservice.api.requests.history.LearningHistoryFilterRequest;
import words.com.wordservice.api.requests.history.StatisticsLearningHistoryFilterRequest;
import words.com.wordservice.api.responds.history.CountLearningHistoryRespond;
import words.com.wordservice.api.responds.history.LearningHistoryRespond;
import words.com.wordservice.api.responds.history.StatisticsLearningHistoryRespond;
import words.com.wordservice.domain.models.filters.history.CountLearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.LearningHistoryFilter;
import words.com.wordservice.domain.models.filters.history.StatisticsLearningHistoryFilter;
import words.com.wordservice.domain.models.history.CountLearningHistory;
import words.com.wordservice.domain.models.history.LearningHistory;
import words.com.wordservice.domain.models.history.StatisticsLearningHistory;

import java.util.Collection;
import java.util.Collections;

@Component
public class LearningHistoryApiMapper {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public LearningHistoryFilter toModel(User user, LearningHistoryFilterRequest request){
        var filter = objectMapper.convertValue(request, LearningHistoryFilter.class);
        if (user.role() == Role.ADMINISTRATION){
            return filter;
        }
        return filter.toBuilder()
                .userIds(Collections.singleton(user.id()))
                .build();
    }

    public StatisticsLearningHistoryFilter toModel(User user, StatisticsLearningHistoryFilterRequest request){
        var filter = objectMapper.convertValue(request, StatisticsLearningHistoryFilter.class);
        if (user.role() == Role.ADMINISTRATION){
            return filter;
        }
        return filter.toBuilder()
                .userIds(Collections.singleton(user.id()))
                .build();
    }

    public CountLearningHistoryFilter toModel(User user, CountLearningHistoryFilterRequest request){
        var filter = objectMapper.convertValue(request, CountLearningHistoryFilter.class);
        if (user.role() == Role.ADMINISTRATION){
            return filter;
        }
        return filter.toBuilder()
                .userIds(Collections.singleton(user.id()))
                .build();
    }

    public LearningHistoryRespond toRespond(LearningHistory model){
        return objectMapper.convertValue(model, LearningHistoryRespond.class);
    }

    public StatisticsLearningHistoryRespond toRespond(StatisticsLearningHistory model){
        return objectMapper.convertValue(model, StatisticsLearningHistoryRespond.class);
    }

    public CountLearningHistoryRespond toRespond(CountLearningHistory model){
        return objectMapper.convertValue(model, CountLearningHistoryRespond.class);
    }
}
