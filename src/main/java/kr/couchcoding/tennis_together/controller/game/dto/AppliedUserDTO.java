package kr.couchcoding.tennis_together.controller.game.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppliedUserDTO {

    @ApiModelProperty(value = "Game 신청 아이디", example = "1", required = true)
    private Long gameUserNo;
    @ApiModelProperty(value = "Game에 신청한 User", required = true)
    private UserDTO gameUser;
    @ApiModelProperty(value = "Game 신청 상태", example = "APPLYING", required = true)
    private GameUserListStatus status;
    @ApiModelProperty(value = "Game 신청일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "Game 신청 수정일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime updDtm;

    public AppliedUserDTO(GameUserList gameUserList) {
        gameUserNo = gameUserList.getGameUserNo();
        gameUser = new UserDTO(gameUserList.getGameUser());
        status = gameUserList.getStatus();
        regDtm = gameUserList.getRegDtm();
        updDtm = gameUserList.getUpdDtm();
    }
}
