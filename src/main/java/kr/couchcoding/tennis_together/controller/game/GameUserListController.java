package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.domain.game.service.GameUserListService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameUserListController {

    private final GameUserListService gameUserListService;

    @PostMapping("/{gameNo}/apply")
    public void applyGame(@PathVariable Long gameNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameUserListService.applyGame(user, gameNo);
    }

    @PostMapping("/{gameNo}/cancel")
    public void cancelAppliedGame(@PathVariable Long gameNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameUserListService.cancelAppliedGame(user, gameNo);
    }

    @PostMapping("/{gameNo}/approve/{joinedUserUid}")
    public void approveAppliedGame(@PathVariable Long gameNo, Authentication authentication,
                                   @PathVariable String joinedUserUid) {
        User gameCreator = ((User) authentication.getPrincipal());
        gameUserListService.approveAppliedGame(gameCreator, gameNo, joinedUserUid);
    }

    @PostMapping("/{gameNo}/refuse/{joinedUserUid}")
    public void refuseAppliedGame(@PathVariable Long gameNo, Authentication authentication,
                                  @PathVariable String joinedUserUid) {
        User gameCreator = ((User) authentication.getPrincipal());
        gameUserListService.refuseAppliedGame(gameCreator, gameNo, joinedUserUid);
    }
}
