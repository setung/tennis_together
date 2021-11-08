package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.controller.game.dto.PostGameDTO;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.service.GameService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
}
