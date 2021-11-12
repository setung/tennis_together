package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.controller.game.dto.RequestGameDTO;
import kr.couchcoding.tennis_together.controller.game.dto.ResponseGameDTO;
import kr.couchcoding.tennis_together.controller.game.specification.GameSpecification;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.service.GameService;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseGameDTO postGame(@RequestBody RequestGameDTO postGameDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        return new ResponseGameDTO(gameService.postGame(user, postGameDTO));
    }

    @PatchMapping("/{gameNo}")
    public void updateGame(@PathVariable Long gameNo, @RequestBody RequestGameDTO updatedGameDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameService.updateGame(user, gameNo, updatedGameDTO);
    }

    @GetMapping
    public Page<ResponseGameDTO> findGames(
            @RequestParam(required = false) Long courtNo,
            @RequestParam(required = false) String uid,
            @RequestParam(required = false) Double score,
            @RequestParam(required = false) String genderType,
            @RequestParam(required = false) Integer ageType,
            @RequestParam(required = false) Integer historyType,
            @RequestParam(required = false) GameStatus status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate strDt,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDt,
            @PageableDefault(sort = "regDtm", direction = Sort.Direction.DESC) Pageable pageable) {

        Specification<Game> spec = (root, query, criteriaBuilder) -> null;
        if (courtNo != null) spec = spec.and(GameSpecification.equalCourtNo(courtNo));
        if (uid != null) spec = spec.and(GameSpecification.equalUid(uid));
        if (genderType != null) spec = spec.and(GameSpecification.equalGenderType(genderType));
        if (ageType != null) spec = spec.and(GameSpecification.equalAgeType(ageType));
        if (historyType != null) spec = spec.and(GameSpecification.equalHistoryType(historyType));
        if (strDt != null) spec = spec.and(GameSpecification.geStrDt(strDt));
        if (endDt != null) spec = spec.and(GameSpecification.leEndDt(endDt));
        if (score != null) spec = spec.and(GameSpecification.geScore(score));
        if (status != null) {
            spec = spec.and(GameSpecification.equalStatus(status));
        } else {
            spec = spec.and(GameSpecification.notEqualStatus());
        }

        return gameService.findAll(spec, pageable).map(game -> new ResponseGameDTO(game));
    }

    @GetMapping("/{gameNo}")
    public ResponseGameDTO findGame(@PathVariable Long gameNo) {
        return new ResponseGameDTO(gameService.findGameByNo(gameNo));
    }

    @DeleteMapping("/{gameNo}")
    public void deleteGame(@PathVariable Long gameNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameService.deleteGame(user, gameNo);
    }
}
