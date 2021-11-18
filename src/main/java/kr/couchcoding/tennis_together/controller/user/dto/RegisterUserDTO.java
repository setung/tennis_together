package kr.couchcoding.tennis_together.controller.user.dto;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.Data;

@Data
public class RegisterUserDTO {
    private String phone;
    private String nickname;
    private String birth;
    private String gender;
    private Integer history;
    private String locSd;
    private String locSkk;
}