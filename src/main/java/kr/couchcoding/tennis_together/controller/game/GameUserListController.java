package kr.couchcoding.tennis_together.controller.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.couchcoding.tennis_together.controller.game.dto.AppliedUserDTO;
import kr.couchcoding.tennis_together.controller.game.dto.ApplyGameDTO;
import kr.couchcoding.tennis_together.controller.game.dto.PlayGameHistoryDTO;
import kr.couchcoding.tennis_together.controller.game.specification.GameUserListSpecification;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.service.GameUserListService;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Api(tags = {"Games 모집관련 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameUserListController {

    private final GameUserListService gameUserListService;

    @ApiOperation(value = "Game의 모집에 신청하는 API")
    @PostMapping("/{gameNo}/apply")
    public void applyGame(@PathVariable Long gameNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameUserListService.applyGame(user, gameNo);
    }

    @ApiOperation(value = "Game의 모집신청에 취소하는 API")
    @PostMapping("/{gameNo}/cancel")
    public void cancelAppliedGame(@PathVariable Long gameNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameUserListService.cancelAppliedGame(user, gameNo);
    }

    @ApiOperation(value = "Game의 모집신청에 승인하는 API")
    @PostMapping("/{gameNo}/approve/{joinedUserUid}")
    public void approveAppliedGame(@PathVariable Long gameNo, Authentication authentication,
                                   @PathVariable String joinedUserUid) {
        User gameCreator = ((User) authentication.getPrincipal());
        gameUserListService.approveAppliedGame(gameCreator, gameNo, joinedUserUid);
    }

    @ApiOperation(value = "Game의 모집신청에 거절하는 API")
    @PostMapping("/{gameNo}/refuse/{joinedUserUid}")
    public void refuseAppliedGame(@PathVariable Long gameNo, Authentication authentication,
                                  @PathVariable String joinedUserUid) {
        User gameCreator = ((User) authentication.getPrincipal());
        gameUserListService.refuseAppliedGame(gameCreator, gameNo, joinedUserUid);
    }

    @ApiOperation(value = "특정 Game에 모집신청한 User 조회 API")
    @GetMapping("/{gameNo}/users")
    public Page<AppliedUserDTO> getUsers(@PathVariable Long gameNo,
                                         @RequestParam(required = false) String gender,
                                         @RequestParam(required = false) Integer history,
                                         @RequestParam(required = false) Long score,
                                         @RequestParam(required = false) GameUserListStatus status,
                                         Pageable pageable) {

        Specification<GameUserList> spec = GameUserListSpecification.equalGameNo(gameNo);

        if (gender != null) spec = spec.and(GameUserListSpecification.equalGender(gender));
        if (gender != null) spec = spec.and(GameUserListSpecification.equalGender(gender));
        if (history != null) spec = spec.and(GameUserListSpecification.equalHistory(history));
        if (score != null) spec = spec.and(GameUserListSpecification.equalScore(score));
        if (status != null) spec = spec.and(GameUserListSpecification.equalStatus(status));

        return gameUserListService.findByGameNo(spec, pageable).map(gameUserList -> new AppliedUserDTO(gameUserList));
    }

    @ApiOperation(value = "Game에 참여한 이력 조회 API")
    @GetMapping("/histories/playgames")
    public Page<PlayGameHistoryDTO> findHistoriesPlayedGame(Authentication authentication, Pageable pageable) {
        User user = ((User) authentication.getPrincipal());

        Specification<GameUserList> spec = (root, query, criteriaBuilder) -> null;
        spec = spec.or(GameUserListSpecification.equalGameCreator(user.getUid()));
        spec = spec.or(GameUserListSpecification.equalGameUser(user.getUsername()));
        spec = spec.and(GameUserListSpecification.notEqualGameStatus(GameStatus.RECRUITING));
        spec = spec.and(GameUserListSpecification.lessThanEndDt(LocalDate.now()));
        spec = spec.and(GameUserListSpecification.equalStatus(GameUserListStatus.APPROVED));

        Page<GameUserList> gameUserLists = gameUserListService.findHistoriesPlayedGame(spec, pageable);
        return gameUserLists.map(gameUserList -> new PlayGameHistoryDTO(user, gameUserList));
    }

    @ApiOperation(value = "Game에 신청한 이력 조회 API")
    @GetMapping("/histories/applygames")
    public Page<ApplyGameDTO> findHistoriesApplyGames(@RequestParam(required = false) GameStatus gameStatus,
                                                      @RequestParam(required = false) GameUserListStatus status,
                                                      Authentication authentication, Pageable pageable) {
        User user = ((User) authentication.getPrincipal());
        Specification<GameUserList> spec = GameUserListSpecification.equalGameUser(user.getUsername());

        if (gameStatus != null) spec = spec.and(GameUserListSpecification.equalGameStatus(gameStatus));
        if (status != null) spec = spec.and(GameUserListSpecification.equalStatus(status));

        Page<GameUserList> gameUserLists = gameUserListService.findHistoriesPlayedGame(spec, pageable);
        return gameUserLists.map(gameUserList -> new ApplyGameDTO(gameUserList));
    }
}
