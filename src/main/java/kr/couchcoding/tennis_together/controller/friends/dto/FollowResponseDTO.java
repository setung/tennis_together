package kr.couchcoding.tennis_together.controller.friends.dto;


import kr.couchcoding.tennis_together.domain.friend.model.FrdList;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class FollowResponseDTO {


    private Long frdRelNo; //DB에서 BigInteger
    private User frdUser; // UserDTO로 변경하기
    private Character relStCd;
    private LocalDateTime regDtm;


    public FollowResponseDTO(FrdList frdList) {
        this.frdRelNo = frdList.getFrdRelNo();
        this.frdUser = frdList.getFrdUser();
        this.relStCd = frdList.getRelStCd();
        this.regDtm = frdList.getRegDtm();
    }
}