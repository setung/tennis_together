package kr.couchcoding.tennis_together.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Data;
@Data
public class UserDTO {

    @ApiModelProperty(value = "User uid", example = "test", required = true)
    private String uid;
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
    @ApiModelProperty(value = "User 상태", example = "1", required = true)
    private Character actDvCd = '1';
    @ApiModelProperty(value = "User 프로필 Img Url", example = "~/image.jpg", required = true)
    private String profileUrl;
    @ApiModelProperty(value = "User 평점", example = "4", required = true)
    private Long score;
    @ApiModelProperty(value = "User 위치정보", required = true)
    private LocCd locCd;

    public UserDTO(User user){
        uid = user.getUid();
        phone = user.getPhone();
        nickname = user.getNickname();
        birth = user.getBirth();
        gender = user.getGender();
        history = user.getHistory();
        actDvCd = user.getActDvCd();
        profileUrl = user.getProfileUrl();
        score = user.getScore();
        locCd = user.getLocCd();
    }

}
