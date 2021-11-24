package kr.couchcoding.tennis_together.controller.friends.dto;


import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.controller.user.dto.UserDTO;
import kr.couchcoding.tennis_together.domain.friend.model.FrdList;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class FollowResponseDTO {

    @ApiModelProperty(value = "친구 기본키", example = "1", required = true)
    private Long frdRelNo; //DB에서 BigInteger
    @ApiModelProperty(value = "친구(User) 정보", required = true)
    private User frdUser; // UserDTO로 변경하기
    @ApiModelProperty(value = "상태 코드", example = "1", required = true)
    private Character relStCd;
    @ApiModelProperty(value = "친구 등록일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime regDtm;

    public FollowResponseDTO(FrdList frdList) {
        this.frdRelNo = frdList.getFrdRelNo();
        this.frdUser = frdList.getFrdUser();
        this.relStCd = frdList.getRelStCd();
        this.regDtm = frdList.getRegDtm();
    }
}