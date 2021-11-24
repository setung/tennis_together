package kr.couchcoding.tennis_together.controller.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = {"모집글(Games)관련 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @ApiOperation(value = "Game 등록 API")
    @PostMapping
    public ResponseGameDTO postGame(@RequestBody RequestGameDTO postGameDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        return new ResponseGameDTO(gameService.postGame(user, postGameDTO));
    }

    @ApiOperation(value = "Game 수정 API")
    @PatchMapping("/{gameNo}")
    public void updateGame(@PathVariable Long gameNo, @RequestBody RequestGameDTO updatedGameDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameService.updateGame(user, gameNo, updatedGameDTO);
    }

    @ApiOperation(value = "Game 전체 조회 API")
    @GetMapping
    public Page<ResponseGameDTO> findGames(
            @ApiParam(value = "Game 아이디") @RequestParam(required = false) Long courtNo,
            @ApiParam(value = "Game 생성자 Uid") @RequestParam(required = false) String uid,
            @ApiParam(value = "Game ScoreType") @RequestParam(required = false) Double score,
            @ApiParam(value = "Game GenderType") @RequestParam(required = false) String genderType,
            @ApiParam(value = "Game AgeType") @RequestParam(required = false) Integer ageType,
            @ApiParam(value = "Game HistoryType") @RequestParam(required = false) Integer historyType,
            @ApiParam(value = "Game 위치 시도") @RequestParam(required = false) String locSd,
            @ApiParam(value = "Game 위치 시군구") @RequestParam(required = false) String locSkk,
            @ApiParam(value = "Game 상태") @RequestParam(required = false) GameStatus status,
            @ApiParam(value = "Game 모집 시작일") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate strDt,
            @ApiParam(value = "Game 모집 종료일") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDt,
            @ApiParam(value = "Game 등록일") @PageableDefault(sort = "regDtm", direction = Sort.Direction.DESC) Pageable pageable) {

        Specification<Game> spec = (root, query, criteriaBuilder) -> null;
        if (courtNo != null) spec = spec.and(GameSpecification.equalCourtNo(courtNo));
        if (uid != null) spec = spec.and(GameSpecification.equalUid(uid));
        if (genderType != null) spec = spec.and(GameSpecification.equalGenderType(genderType));
        if (ageType != null) spec = spec.and(GameSpecification.equalAgeType(ageType));
        if (historyType != null) spec = spec.and(GameSpecification.equalHistoryType(historyType));
        if (strDt != null) spec = spec.and(GameSpecification.geStrDt(strDt));
        if (endDt != null) spec = spec.and(GameSpecification.leEndDt(endDt));
        if (locSd != null) spec = spec.and(GameSpecification.equalLocSd(locSd));
        if (locSkk != null) spec = spec.and(GameSpecification.equalLocSkk(locSkk));
        if (score != null) spec = spec.and(GameSpecification.geScore(score));
        if (status != null) {
            spec = spec.and(GameSpecification.equalStatus(status));
        } else {
            spec = spec.and(GameSpecification.notDelete());
        }

        return gameService.findAll(spec, pageable).map(game -> new ResponseGameDTO(game));
    }

    @ApiOperation(value = "Game 상세 조회 API")
    @GetMapping("/{gameNo}")
    public ResponseGameDTO findGame(@ApiParam(value = "Game 아이디") @PathVariable Long gameNo) {
        return new ResponseGameDTO(gameService.findGameByNo(gameNo));
    }

    @ApiOperation(value = "Game 삭제 API")
    @DeleteMapping("/{gameNo}")
    public void deleteGame(@ApiParam(value = "Game 아이디") @PathVariable Long gameNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        gameService.deleteGame(user, gameNo);
    }
}
