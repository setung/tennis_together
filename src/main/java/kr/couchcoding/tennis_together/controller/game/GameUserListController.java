package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.domain.game.service.GameService;
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
        gameUserListService.applyGame(user,gameNo);
    }
}
