package kr.couchcoding.tennis_together.controller.game.dto;

import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplyGameDTO {

    private Long gameUserNo;
    private ResponseGameDTO joinedGame;
    private GameUserListStatus status;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;

    public ApplyGameDTO(GameUserList gameUserList) {
        gameUserNo = gameUserList.getGameUserNo();
        joinedGame = new ResponseGameDTO(gameUserList.getJoinedGame());
        status = gameUserList.getStatus();
        regDtm = gameUserList.getRegDtm();
        updDtm = gameUserList.getUpdDtm();
    }
}
