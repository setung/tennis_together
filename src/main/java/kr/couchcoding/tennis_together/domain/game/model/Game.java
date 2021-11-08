package kr.couchcoding.tennis_together.domain.game.model;

import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.user.model.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_info")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_no")
    private Long gameNo;

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "uid")
    private User gameCreator; // writer_id 칼럼을 객체로


    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "gender_type")
    private Character genderType;

    @Column(name = "age_type")
    private Character ageType;

    @Column(name = "str_dt", length = 8)
    private String strDt;

    @Column(name = "end_dt", length = 8)
    private String endDt;

    @Column(nullable = false, name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(nullable = false, name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private LocCd locCd;

    @Column(name = "st_dv_cd")
    private char stDvCd = '1';

    @Builder
    public Game(long gameNo, User gameCreator, String title, String content, char genderType,
                char ageType, String strDt, String endDt,
                LocCd locCd){
        this.gameNo = gameNo;
        this.gameCreator = gameCreator;
        this.title = title;
        this.content = content;
        this.genderType = genderType;
        this.ageType = ageType;
        this.strDt = strDt;
        this.endDt = endDt;
        this.locCd = locCd;
    }
}
