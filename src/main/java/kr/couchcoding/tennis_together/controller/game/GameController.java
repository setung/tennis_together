package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.controller.game.dto.PostGameDTO;
import kr.couchcoding.tennis_together.controller.game.dto.ResponseGameDTO;
import kr.couchcoding.tennis_together.controller.game.specification.GameSpecification;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.service.GameService;
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

import javax.transaction.Transactional;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
@Transactional
public class GameController {

    private final GameService gameService;
    private final CourtService courtService;

    @PostMapping
    public void postGame(@RequestBody PostGameDTO postGameDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        Court court = courtService.findCourtByNo(postGameDTO.getCourtNo());

        Game game = Game.builder()
                .gameCreator(user)
                .court(court)
                .title(postGameDTO.getTitle())
                .content(postGameDTO.getContent())
                .historyType(postGameDTO.getHistoryType())
                .ageType(postGameDTO.getAgeType())
                .genderType(postGameDTO.getGenderType())
                .strDt(postGameDTO.getStrDt())
                .endDt(postGameDTO.getEndDt())
                .build();

        gameService.postGame(game);
    }

    @PatchMapping("/{gameNo}")
    public void updateGame(@PathVariable Long gameNo, @RequestBody PostGameDTO gameDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        Game game = gameService.findGameByGameNoAndGameCreator(gameNo, user);
        Court court = null;

        if (gameDTO.getCourtNo() != null)
            court = courtService.findCourtByNo(gameDTO.getCourtNo());

        Game updatedGame = Game.builder()
                .title(gameDTO.getTitle())
                .content(gameDTO.getContent())
                .historyType(gameDTO.getHistoryType())
                .genderType(gameDTO.getGenderType())
                .ageType(gameDTO.getAgeType())
                .strDt(gameDTO.getStrDt())
                .endDt(gameDTO.getEndDt())
                .court(court)
                .build();

        gameService.updateGame(game, updatedGame);
    }

    @GetMapping
    public Page<ResponseGameDTO> findGames(
            @RequestParam(required = false) Long courtNo,
            @RequestParam(required = false) String uid,
            @RequestParam(required = false) Double score,
            @RequestParam(required = false) String genderType,
            @RequestParam(required = false) Integer ageType,
            @RequestParam(required = false) Integer historyType,
            @RequestParam(required = false) Character status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate strDt,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDt,
            @PageableDefault(sort = "regDtm", direction = Sort.Direction.DESC) Pageable pageable) {

        Specification<Game> spec = (root, query, criteriaBuilder) -> null;
        if (courtNo != null) spec = spec.and(GameSpecification.equalCourtNo(courtNo));
        if (uid != null) spec = spec.and(GameSpecification.equalUid(uid));
        if (genderType != null) spec = spec.and(GameSpecification.equalGenderType(genderType));
        if (ageType != null) spec = spec.and(GameSpecification.equalAgeType(ageType));
        if (historyType != null) spec = spec.and(GameSpecification.equalHistoryType(historyType));
        if (status != null) spec = spec.and(GameSpecification.equalStatus(status));
        if (strDt != null) spec = spec.and(GameSpecification.geStrDt(strDt));
        if (endDt != null) spec = spec.and(GameSpecification.leEndDt(endDt));
        if (score != null) spec = spec.and(GameSpecification.geScore(score));

        return gameService.findAll(spec, pageable).map(game -> new ResponseGameDTO(game));
    }
}
