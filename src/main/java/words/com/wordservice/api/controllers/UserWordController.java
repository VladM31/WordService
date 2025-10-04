package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.UserWordApiMapper;
import words.com.wordservice.api.requests.words.CreateUserWordRequest;
import words.com.wordservice.api.requests.words.DeleteUserWordRequest;
import words.com.wordservice.api.requests.words.PinUserWordRequest;
import words.com.wordservice.api.requests.words.UserWordFilterRequest;
import words.com.wordservice.api.responds.UserWordRespond;
import words.com.wordservice.domain.services.UserWordService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/user-words")
@RequiredArgsConstructor
public class UserWordController {
    private final UserWordService userWordService;
    private final UserWordApiMapper userWordApiMapper;


    @GetMapping
    public PagedModel<UserWordRespond> getAll(@AuthenticationPrincipal User user, @ModelAttribute UserWordFilterRequest filter){
        var filterModel = userWordApiMapper.toFilter(user, filter);
        var paged = userWordService.findBy(filterModel).map(userWordApiMapper::toRespond);
        return new PagedModel<>(paged);
    }

    @PostMapping
    public void create(@AuthenticationPrincipal User user, @RequestBody @Valid Set<CreateUserWordRequest> words){
        var models = words.stream().map(word -> userWordApiMapper.toModel(user,word)).toList();
        userWordService.save(models);
    }

    @PostMapping("pin")
    public void savePin(@AuthenticationPrincipal User user, @RequestBody @Valid Set<PinUserWordRequest> requests){
        var models = requests.stream().map(it -> userWordApiMapper.toModel(user,it)).toList();
        userWordService.savePins(models);
    }

    @PostMapping("delete")
    public void delete(@AuthenticationPrincipal User user, @RequestBody @Valid List<DeleteUserWordRequest> requests){
        userWordService.delete(
                requests.stream().map(request -> userWordApiMapper.toOptions(request, user.id())).collect(Collectors.toSet())
        );
    }
}
