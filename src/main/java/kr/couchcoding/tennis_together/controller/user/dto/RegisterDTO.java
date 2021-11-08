package kr.couchcoding.tennis_together.controller.user.dto;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.Data;

@Data
public class RegisterDTO {
    private String phone;
    private String name;
    private String nickname;
    private String birth;
    private Character gender;
    private Integer history;
    private String profileUrl;
    private LocCd locCd;
}
