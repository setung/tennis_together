package kr.couchcoding.tennis_together.domain.game.model;

import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_user_list")
public class GameUserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_user_no")
    private Long gameUserNo;

    // Game 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "game_no")
    private Game joinedGame; // game_no 칼럼을 객체로

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "uid")
    private User gameUser; // uid 칼럼을 객체로

    @Column(name = "st_dv_cd")
    @Enumerated(EnumType.ORDINAL)
    private GameUserListStatus stDvCd;

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @Builder
    public GameUserList(User gameUser, Game joinedGame, GameUserListStatus stDvCd) {
        this.joinedGame = joinedGame;
        this.gameUser = gameUser;
        this.stDvCd = stDvCd;
    }
}
