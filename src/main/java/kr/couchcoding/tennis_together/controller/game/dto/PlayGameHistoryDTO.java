package kr.couchcoding.tennis_together.controller.game.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlayGameHistoryDTO {

    @ApiModelProperty(value = "Game 신청 아이디", example = "1", required = true)
    private Long gameUserNo;
    @ApiModelProperty(value = "신청한 Game 정보", required = true)
    private ResponseGameDTO joinedGame;
    @ApiModelProperty(value = "같이 게임한 User 정보", required = true)
    private UserDTO userPlayedWith;
    @ApiModelProperty(value = "Game 신청일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "Game 신청 수정일", example = "2021-11-22 00:47:51", required = true)
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
