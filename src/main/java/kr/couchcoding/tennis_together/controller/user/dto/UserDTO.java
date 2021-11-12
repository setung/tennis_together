package kr.couchcoding.tennis_together.controller.user.dto;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Data;
@Data
public class UserDTO {

    private String uid;
    private String phone;
    private String name;
    private String nickname;
    private String birth;
    private Character gender;
    private Integer history;
    private Character actDvCd = '1';
    private String profileUrl;
    private Long score;
    private LocCd locCd;

    public UserDTO(User user){
        uid = user.getUid();
        phone = user.getPhone();
        name = user.getName();
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
