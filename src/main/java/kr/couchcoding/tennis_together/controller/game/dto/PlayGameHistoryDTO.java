package kr.couchcoding.tennis_together.controller.game.dto;

import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlayGameHistoryDTO {

    private Long gameUserNo;
    private ResponseGameDTO joinedGame;
    private UserDTO userPlayedWith;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;

    public PlayGameHistoryDTO(User user, GameUserList gameUserList) {
        gameUserNo = gameUserList.getGameUserNo();
        joinedGame = new ResponseGameDTO(gameUserList.getJoinedGame());

        if (gameUserList.getGameUser().getUsername().equals(user.getUsername())) {
            userPlayedWith = new UserDTO(gameUserList.getJoinedGame().getGameCreator());
        } else userPlayedWith = new UserDTO(gameUserList.getGameUser());

        regDtm = gameUserList.getRegDtm();
        updDtm = gameUserList.getUpdDtm();
    }
}
