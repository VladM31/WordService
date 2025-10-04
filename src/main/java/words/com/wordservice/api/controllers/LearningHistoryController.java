package words.com.wordservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.LearningHistoryApiMapper;
import words.com.wordservice.api.requests.history.CountLearningHistoryFilterRequest;
import words.com.wordservice.api.requests.history.LearningHistoryFilterRequest;
import words.com.wordservice.api.requests.history.StatisticsLearningHistoryFilterRequest;
import words.com.wordservice.api.responds.history.CountLearningHistoryRespond;
import words.com.wordservice.api.responds.history.LearningHistoryRespond;
import words.com.wordservice.api.responds.history.StatisticsLearningHistoryRespond;
import words.com.wordservice.domain.services.LearningHistoryService;

@RestController
@ResponseBody
@RequestMapping("/learning-history")
@RequiredArgsConstructor
public class LearningHistoryController {
    private final LearningHistoryService learningHistoryService;
    private final LearningHistoryApiMapper mapper;

    @GetMapping
    public PagedModel<LearningHistoryRespond> getLearningHistory(
            @AuthenticationPrincipal User user,
            @ModelAttribute LearningHistoryFilterRequest request) {
        var filter = mapper.toModel(user, request);
        var paged = learningHistoryService.findBy(filter).map(mapper::toRespond);
        return new PagedModel<>(paged);
    }

    @GetMapping("/statistics")
    public PagedModel<StatisticsLearningHistoryRespond> getStatisticsLearningHistory(
            @AuthenticationPrincipal User user,
            @ModelAttribute StatisticsLearningHistoryFilterRequest request) {
        var filter = mapper.toModel(user, request);
        var paged = learningHistoryService.findBy(filter).map(mapper::toRespond);
        return new PagedModel<>(paged);
    }

    @GetMapping("/count")
    public PagedModel<CountLearningHistoryRespond> getCountLearningHistory(
            @AuthenticationPrincipal User user,
            @ModelAttribute CountLearningHistoryFilterRequest request) {
        var filter = mapper.toModel(user, request);
        var paged = learningHistoryService.findBy(filter).map(mapper::toRespond);
        return new PagedModel<>(paged);
    }
}
