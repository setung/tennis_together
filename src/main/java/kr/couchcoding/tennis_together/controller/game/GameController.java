package kr.couchcoding.tennis_together.controller.game;

import kr.couchcoding.tennis_together.controller.game.dto.PostGameDTO;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.service.GameService;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
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
}
