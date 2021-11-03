package kr.couchcoding.tennis_together.domain.game.model;

import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.user.model.User;

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
    private char stDvCd = '1';

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @Builder
    public GameUserList(User gameUser, Game joinedGame){
        this.joinedGame = joinedGame;
        this.gameUser = gameUser;
    }
}
