package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.LearningPlanApiMapper;
import words.com.wordservice.api.requests.plan.LearningPlanRequest;
import words.com.wordservice.api.responds.plan.LearningPlanRespond;
import words.com.wordservice.domain.services.LearningPlanService;

import javax.annotation.Nullable;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-plan")
public class LearningPlanController {
    private final LearningPlanService learningPlanService;
    private final LearningPlanApiMapper mapper;

    @Nullable
    @GetMapping
    LearningPlanRespond getLearningPlan(@AuthenticationPrincipal User user) {
        return learningPlanService.findByUserId(user.id())
                .map(mapper::toRespond)
                .orElse(null);
    }

    @PostMapping
    @Validated
    LearningPlanRespond createLearningPlan(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid LearningPlanRequest request) {
        var model = mapper.toModel(request, user.id());
        var result = learningPlanService.create(model);
        return mapper.toRespond(result);
    }


    @PutMapping
    @Validated
    void update(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid LearningPlanRequest request) {
        var model = mapper.toModel(request, user.id());
        learningPlanService.update(model);
    }
}
