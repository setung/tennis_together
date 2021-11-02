package kr.couchcoding.tennis_together.domain;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "game_comment")
public class GameComment {

    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentNo;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(nullable = false, name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(nullable = false, name = "upd_dtm")
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
            long commentNo, String reviewTitle, String reviewContent, LocalDateTime regDtm, LocalDateTime updDtm
            , int depth, int grpNo, Game commentedGame, User comtWriteUser){
        this.commentNo = commentNo;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
        this.depth = depth;
        this.grpNo = grpNo;
        this.commentedGame = commentedGame;
        this.comtWriteUser = comtWriteUser;


    }

}
