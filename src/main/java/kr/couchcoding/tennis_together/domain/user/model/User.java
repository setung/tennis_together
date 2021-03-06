package kr.couchcoding.tennis_together.domain.user.model;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor // 기본 생성자 생성
@ToString
@Table(name = "user_info")
@EqualsAndHashCode(of = "uid")
public class User implements UserDetails {

    @Id
    @Column(length = 50)//, columnDefinition = "firebase uid")
    private String uid;

    @Column(length = 10)//, columnDefinition = "휴대폰 번호")
    private String phone;

    // 유저 이름 칼럼 삭제
    //@Column(nullable = false, length = 50) // 디폴트는 nullalbe = true, length = 255
    //private String name;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 6)
    private String birth;

    // gender 타입 변경 Character -> String
    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer history;

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @Column(nullable = false, name = "act_dv_cd")
    private Character actDvCd = '1';

    @Column(length = 100, name = "profile_url")
    private String profileUrl;

    private Long score;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private LocCd locCd;

    /*
     * Entity에는 Setter를 사용하지 않는다.
     *  - 객체의 일관성을 유지하는데 문제가 생길 수 있기 때문에
     *  - 객체는 생성할 때는 Builder를 사용
     * */
    @Builder
    public User(String uid, String phone,  String nickname, String birth, String gender,
                Integer history, String profileUrl, Long score, LocCd locCd){

        this.uid = uid;
        this.phone = phone;
        //this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.history = history;
        this.profileUrl = profileUrl;
        this.score = score;
        this.locCd = locCd;
    }

    public void updateScore(long score) {
        this.score = score;
    }

    public User(User registeredUser) {
    }

    public void UpdateUser(User updateUser) {
        if (updateUser.getPhone() != null) phone = updateUser.getPhone();
        if (updateUser.getBirth() != null) birth = updateUser.getBirth();
        if (updateUser.getHistory() != null) history = updateUser.getHistory();
        if (updateUser.getGender() != null) gender = updateUser.getGender();
        if (updateUser.getNickname() != null) nickname = updateUser.getNickname();
        if (updateUser.getLocCd().getLocCdNo() != null) locCd = updateUser.getLocCd();
        if (updateUser.getProfileUrl() != null) profileUrl = updateUser.getProfileUrl();
    }

    public void updateUserActDvCd(Character actDvCd )
    {
        this.actDvCd = actDvCd;
    }

    public void updateUserProfileUrl(String profileUrl){ this.profileUrl = profileUrl; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        return uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return actDvCd == '1';
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        return actDvCd == '1';
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

}
