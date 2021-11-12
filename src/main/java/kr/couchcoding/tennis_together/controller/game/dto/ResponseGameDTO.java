package kr.couchcoding.tennis_together.controller.game.dto;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseGameDTO {

    private Long gameNo;
    private User gameCreator;
    private String title;
    private String content;
    private String genderType;
    private Integer ageType;
    private Integer historyType;
    private LocalDate strDt;
    private LocalDate endDt;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;
    private Court court;
    private GameStatus stDvCd;

    public ResponseGameDTO(Game game) {
        gameNo = game.getGameNo();
        gameCreator = game.getGameCreator();
        title = game.getTitle();
        content = game.getContent();
        genderType = game.getGenderType();
        ageType = game.getAgeType();
        historyType = game.getHistoryType();
        strDt = game.getStrDt();
        endDt = game.getEndDt();
        regDtm = game.getRegDtm();
        updDtm = game.getUpdDtm();
        court = game.getCourt();
        stDvCd = game.getGameStatus();
    }
}
