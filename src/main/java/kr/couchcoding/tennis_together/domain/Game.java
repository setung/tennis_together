package kr.couchcoding.tennis_together.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_info")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_no")
    private long gameNo;


    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "uid")
    private User gameCreator; // writer_id 칼럼을 객체로


    @Column(nullable = false)
    private String title;

    private String content;

    @Column(name = "gender_type")
    private char genderType;

    @Column(name = "age_type")
    private int ageType;

    @Column(name = "str_dt")
    private String strDt;

    @Column(name = "end_dt")
    private String endDt;

    @Column(nullable = false, name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(nullable = false, name = "upd_dtm")
    private LocalDateTime updDtm;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private LocCd locCd;

//    @Column(name = "loc_sd")
//    private String locSd;
//    @Column(name = "loc_skk")
//    private String locSkk;
//
    @Column(name = "st_dv_cd")
    private char stDvCd;

    // GameComment 객체와 양방향 매핑 1 : N
    @OneToMany(mappedBy = "commentedGame")
    private List<GameComment> gameComment = new ArrayList<>();

    // GameUser 객체와 양방향 매핑 1 : N
    @OneToMany(mappedBy = "joinedGame")
    private List<GameUserList> gameUserList = new ArrayList<>();

    @Builder
    public Game(long gameNo, User gameCreator, String title, String content, char genderType
                , char ageType, String strDt, String endDt, LocalDateTime regDtm, LocalDateTime updDtm
                , LocCd locCd, char stDvCd){
        this.gameNo = gameNo;
        this.gameCreator = gameCreator;
        this.title = title;
        this.content = content;
        this.genderType = genderType;
        this.ageType = ageType;
        this.strDt = strDt;
        this.endDt = endDt;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
        this.locCd = locCd;
        this.stDvCd = stDvCd;
    }
}
