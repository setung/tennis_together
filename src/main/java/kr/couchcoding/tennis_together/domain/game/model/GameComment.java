package kr.couchcoding.tennis_together.domain.game.model;

import lombok.*;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.couchcoding.tennis_together.domain.user.model.User;

import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_comment")
public class GameComment {

    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentNo;

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

    @Column
    private Long parentNo;



    @Builder
    public GameComment(
            String reviewContent, 
            int depth, int grpNo, Long parentNo, Game commentedGame, User comtWriteUser){
        this.reviewContent = reviewContent;
        this.depth = depth;
        this.grpNo = grpNo;
        this.commentedGame = commentedGame;
        this.comtWriteUser = comtWriteUser;
        this.parentNo = parentNo;
    }



    public void updateComment(String reviewContent) {
        this.reviewContent = reviewContent;
    }

}
