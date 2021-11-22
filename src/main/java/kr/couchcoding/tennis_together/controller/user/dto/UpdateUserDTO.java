package kr.couchcoding.tennis_together.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateUserDTO {

    @ApiModelProperty(value = "User 연락처", example = "010-0000-1234", required = true)
    private String phone;
    @ApiModelProperty(value = "User 닉네임", example = "nickName", required = true)
    private String nickname;
    @ApiModelProperty(value = "User 생년월일", example = "901111", required = true)
    private String birth;
    @ApiModelProperty(value = "User 성별", example = "남성", required = true)
    private String gender;
    @ApiModelProperty(value = "User 경력", example = "2", required = true)
    private Integer history;
    @ApiModelProperty(value = "User 위치정보 시도", required = true)
    private String locSd;
    @ApiModelProperty(value = "User 위치정보 시군구", required = true)
    private String locSkk;
    @ApiModelProperty(value = "User 프로필 Img Url", example = "~/image.jpg", required = true)
    private String profileUrl;
}
