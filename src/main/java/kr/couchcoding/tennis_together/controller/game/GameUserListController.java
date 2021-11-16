package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.controller.game.dto.AppliedUserDTO;
import kr.couchcoding.tennis_together.controller.game.specification.GameUserListSpecification;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.service.GameUserListService;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{gameNo}/users")
    public Page<AppliedUserDTO> getUsers(@PathVariable Long gameNo,
                                         @RequestParam String gender,
                                         @RequestParam Integer history,
                                         @RequestParam Long score,
                                         @RequestParam GameUserListStatus status,
                                         Pageable pageable) {

        Specification<GameUserList> spec = (root, query, criteriaBuilder) -> null;

        if(gender != null) spec = spec.and(GameUserListSpecification.equalGender(gender));
        if(history != null) spec = spec.and(GameUserListSpecification.equalHistory(history));
        if(score != null) spec = spec.and(GameUserListSpecification.equalScore(score));
        if(status != null) spec = spec.and(GameUserListSpecification.equalStatus(status));

        return gameUserListService.findByGameNo(gameNo, spec, pageable).map(gameUserList -> new AppliedUserDTO(gameUserList));
    }
}
