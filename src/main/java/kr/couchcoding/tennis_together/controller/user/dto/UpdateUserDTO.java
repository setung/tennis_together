package kr.couchcoding.tennis_together.controller.user.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String phone;
    private String nickname;
    private String birth;
    private String gender;
    private Integer history;
    private String locSd;
    private String locSkk;
    private String profileUrl;
}
