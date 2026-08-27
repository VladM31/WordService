package words.com.wordservice.playlist.api.controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.playlist.domain.models.PinPlayListDto;
import words.com.wordservice.playlist.domain.models.UnPinPlayListDto;
import words.com.wordservice.playlist.domain.usercase.PinPlayListUseCase;
import words.com.wordservice.playlist.domain.usercase.UpPinPlayListUseCase;

@Validated
@ResponseBody
@RestController
@RequestMapping("/play-list/{id}")
@RequiredArgsConstructor
public class PinPlayListController {
    private final PinPlayListUseCase pinUseCase;
    private final UpPinPlayListUseCase unPinUseCase;


    @PatchMapping("/pin")
    public void pinPlayList(
            @AuthenticationPrincipal
            User user,
            @Valid
            @NotBlank(message = "Play list id required")
            @PathVariable
            String id
    ) {
        pinUseCase.execute(new PinPlayListDto(user.id(), id));
    }

    @PatchMapping("/unpin")
    public void unpinPlayList(
            @AuthenticationPrincipal
            User user,
            @Valid
            @NotBlank(message = "Play list id required")
            @PathVariable
            String id
    ) {
        unPinUseCase.execute(new UnPinPlayListDto(user.id(), id));
    }
}
