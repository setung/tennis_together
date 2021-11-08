package kr.couchcoding.tennis_together.controller.friends.dto;


import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Data;


@Data
public class FollowResponseDTO {


    private Long frdRelNo;
    private User frdUser; // UserDTO로 변경하기
    private String userName;
    private Long locSd;
    private Long locSkk;
    private String locSdName;
    private String locSkkName;
    private Character regStCd;
    private Long relStCd;


}