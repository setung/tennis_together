package kr.couchcoding.tennis_together.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor // 기본 생성자 생성
@Table(name="user_info")
public class User {

    @Id // 기본 키 핸드폰번호 값으로 직접할당
    private String uid;

    @Column(nullable = false, length = 50) // 디폴트는 nullalbe = true, length = 255
    private String name;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 6)
    private String birth;

    @Column(nullable = false)
    private char gender;

    @Column(nullable = false)
    private int history;

    @Column(nullable = false, name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(nullable = false, name = "upd_dtm")
    private LocalDateTime updDtm;

    @Column(nullable = false, name = "act_dv_cd")
    private char actDvCd;

    @Column(name = "profile_url")
    private String profileUrl;
    private double score;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private  LocCd locCd;


    // UserReview 객체와 양방향 매핑 1 : N
    @OneToMany(mappedBy = "reviewUser")
    private List<UserReview> userReviews = new ArrayList<>();

    // UserReview 객체와 양방향 매핑 1 : N
    //@OneToMany(mappedBy = "writerUser")
    //private List<UserReview> writeReview = new ArrayList<>();

    // Game 객체와 양방향 매핑 1 : N
    @OneToMany(mappedBy = "gameCreator")
    private List<Game> createdGameByMe = new ArrayList<>();

    // GameUser 객체와 매핑 1 : N
    @OneToMany(mappedBy = "gameUser")
    private List<GameUserList> joinedGamesByUser = new ArrayList<>();

    // FrdList 객체와 매핑 1 : N
    @OneToMany(mappedBy = "frdUser")
    private List<FrdList> friendsList = new ArrayList<>();




    /*
    * Entity에는 Setter를 사용하지 않는다.
    *  - 객체의 일관성을 유지하는데 문제가 생길 수 있기 때문에
    *  - 객체는 생성할 때는 Builder를 사용
    * */
    @Builder
    public User(String uid, String name, String password, String nickname, String birth, char gender,
                int history, LocalDateTime regDtm, LocalDateTime updDtm, char actDvCd, String profileUrl,
                double score, LocCd locCd){
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.history = history;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
        this.actDvCd = actDvCd;
        this.profileUrl = profileUrl;
        this.score = score;
        this.locCd = locCd;
    }

}
