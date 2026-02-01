package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.PlayListApiMapper;
import words.com.wordservice.api.requests.playlist.*;
import words.com.wordservice.api.responds.playlist.PlayListCountRespond;
import words.com.wordservice.api.responds.playlist.PlayListRespond;
import words.com.wordservice.domain.models.playlist.DeletePlayList;
import words.com.wordservice.domain.services.WordPlayListService;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/play-list")
@RequiredArgsConstructor
@Validated
public class PlayListController {
    private final WordPlayListService wordPlayListService;
    private final PlayListApiMapper playListApiMapper;


    @GetMapping
    public PagedModel<PlayListRespond> getPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PlayListGetRequest request
    ) {
        var filter = playListApiMapper.toFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/count")
    public PagedModel<PlayListCountRespond> getCountPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PlayListCountGetRequest request
    ) {
        var filter = playListApiMapper.toCountFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/count/public")
    public PagedModel<PlayListCountRespond> getCountPlayLists(
            @Valid PublicPlayListCountGetRequest request
    ) {
        var filter = playListApiMapper.toPublicFilter(request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @PostMapping
    public void save(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid @NotEmpty List<PlayListCreateRequest> playLists) {
        var models = playLists.stream().map( it ->
                playListApiMapper.toModifyPlayList(user, it)
        ).toList();
        wordPlayListService.saveAll(models);
    }

    @PutMapping
    public void update(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<UpdatePlayListRequest> playLists) {
        var models = playLists.stream()
                .map(it -> playListApiMapper.toModifyPlayList(user, it))
                .toList();
        wordPlayListService.updateAll(models);
    }

    @PutMapping("/grades")
    public void updateGrades(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PlayListGradeRequest> grades) {
        wordPlayListService.updateGrades(grades.stream().map( it ->
                playListApiMapper.toPlayListGrade(user, it)
        ).toList());
    }

    @DeleteMapping("/delete")
    public void delete(@AuthenticationPrincipal User user, @RequestParam List<String> ids) {
        wordPlayListService.delete(new DeletePlayList(user.id(), ids));
    }
}
