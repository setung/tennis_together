package kr.couchcoding.tennis_together.controller.game.dto;

import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppliedUserDTO {

    private Long gameUserNo;
    private ResponseGameDTO joinedGame;
    private UserDTO gameUser;
    private GameUserListStatus status;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;

    public AppliedUserDTO(GameUserList gameUserList) {
        gameUserNo = gameUserList.getGameUserNo();
        gameUser = new UserDTO(gameUserList.getGameUser());
        status = gameUserList.getStatus();
        regDtm = gameUserList.getRegDtm();
        updDtm = gameUserList.getUpdDtm();
        joinedGame = new ResponseGameDTO(gameUserList.getJoinedGame());
    }
}
