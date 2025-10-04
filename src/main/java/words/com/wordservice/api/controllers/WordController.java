package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.WordApiMapper;
import words.com.wordservice.api.requests.words.DeleteWordRequest;
import words.com.wordservice.api.requests.words.CreateWordRequest;
import words.com.wordservice.api.requests.words.WordGetRequest;
import words.com.wordservice.api.responds.WordRespond;
import words.com.wordservice.domain.services.WordService;

import java.util.Set;


@RestController
@ResponseBody
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {
    private final WordApiMapper wordApiMapper;
    private final WordService wordService;

    @GetMapping
    PagedModel<WordRespond> get(@AuthenticationPrincipal User user, @Valid @ModelAttribute WordGetRequest request){
        var filter = wordApiMapper.toModel(user,request);
        var paged = wordService.findBy(filter).map(wordApiMapper::toRespond);
        return new PagedModel<>(paged);
    }

    @PostMapping
    void save(@AuthenticationPrincipal User user, @RequestBody @Valid @NotEmpty Set<CreateWordRequest> words){
        var models =  words.stream()
                .map(it -> wordApiMapper.toModifyWord(it, user))
                .toList();
        wordService.saveAll(models);
    }

    @DeleteMapping
    void delete(@RequestBody @Valid DeleteWordRequest request){
        var options = wordApiMapper.toOptions(request);
        wordService.delete(options);
    }

}
