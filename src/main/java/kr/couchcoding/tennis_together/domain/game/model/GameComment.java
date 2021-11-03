package kr.couchcoding.tennis_together.domain.game.model;

import lombok.*;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.user.model.User;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_comment")
public class GameComment {

    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentNo;

    @Column(nullable = false, length = 100, name = "review_title")
    private String reviewTitle;

    @Column(nullable = false, name = "review_content")
    private String reviewContent;

    @Column(nullable = false, name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(nullable = false, name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    private int depth;

    @Column(name = "grp_no")
    private int grpNo;

    // Game 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "game_no")
    private Game commentedGame; // game_no 칼럼을 객체로

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "uid")
    private User comtWriteUser;

    @Builder
    public GameComment(
            String reviewTitle, String reviewContent, 
            int depth, int grpNo, Game commentedGame, User comtWriteUser){
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.depth = depth;
        this.grpNo = grpNo;
        this.commentedGame = commentedGame;
        this.comtWriteUser = comtWriteUser;
    }

}
