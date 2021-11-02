package kr.couchcoding.tennis_together.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_user_list")
public class GameUserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="game_user_no")
    private long gameUserNo;

    // Game 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "game_no")
    private Game joinedGame; // game_no 칼럼을 객체로

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "uid")
    private User gameUser; // uid 칼럼을 객체로


    @Column(name = "st_dv_cd")
    private char stDvCd;

    @Column(name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    private LocalDateTime updDtm;

    public GameUserList(long gameUserNo, User gameUser, Game joinedGame, char stDvCd, LocalDateTime regDtm, LocalDateTime updDtm){

        this.gameUserNo = gameUserNo;
        this.joinedGame = joinedGame;
        this.gameUser = gameUser;
        this.stDvCd = stDvCd;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
    }
}
