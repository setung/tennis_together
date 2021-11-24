package kr.couchcoding.tennis_together.controller.game.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplyGameDTO {

    @ApiModelProperty(value = "Game 신청 아이디", example = "1", required = true)
    private Long gameUserNo;
    @ApiModelProperty(value = "신청한 Game 정보", required = true)
    private ResponseGameDTO joinedGame;
    @ApiModelProperty(value = "Game 신청 상태", example = "APPLYING", required = true)
    private GameUserListStatus status;
    @ApiModelProperty(value = "Game 신청일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "Game 신청 수정일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime updDtm;

    public ApplyGameDTO(GameUserList gameUserList) {
        gameUserNo = gameUserList.getGameUserNo();
        joinedGame = new ResponseGameDTO(gameUserList.getJoinedGame());
        status = gameUserList.getStatus();
        regDtm = gameUserList.getRegDtm();
        updDtm = gameUserList.getUpdDtm();
    }
}
