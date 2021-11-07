package kr.couchcoding.tennis_together.domain.game.model;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_info")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_no")
    private Long gameNo;

    @ManyToOne
    @JoinColumn(name = "user_uid")
    private User gameCreator; // writer_id 칼럼을 객체로

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "gender_type", length = 10)
    private String genderType;

    @Column(name = "age_type")
    private Integer ageType;

    @Column(name = "history_type")
    private Integer historyType;

    @Column(name = "str_dt", length = 8)
    private LocalDate strDt;

    @Column(name = "end_dt", length = 8)
    private LocalDate endDt;

    @Column(nullable = false, name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(nullable = false, name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @ManyToOne
    @JoinColumn(name = "court_no")
    private Court court;

    @Column(name = "st_dv_cd")
    private Character stDvCd = '1';

    @Builder
    public Game(long gameNo, User gameCreator, String title, String content, String genderType,
                Integer historyType, Integer ageType, LocalDate strDt, LocalDate endDt, Court court) {
        this.gameNo = gameNo;
        this.gameCreator = gameCreator;
        this.title = title;
        this.content = content;
        this.historyType = historyType;
        this.genderType = genderType;
        this.ageType = ageType;
        this.strDt = strDt;
        this.endDt = endDt;
        this.court = court;
    }
}
