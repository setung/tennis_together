package kr.couchcoding.tennis_together.domain.userreview.model;

import kr.couchcoding.tennis_together.controller.userreview.dto.RequestUserReviewDTO;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.user.model.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_review")
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private Long reviewNo;

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "written_uid")
    private User writtenUser; // written_uid 칼럼을 객체로

    @OneToOne
    @JoinColumn(name = "recipient_uid")
    private User recipient;  // 리뷰 대상자

    @OneToOne
    @JoinColumn(name = "game_no")
    private Game game;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    // score 타입을 정수형으로 변경
    @Column(name = "score")
    private Long score;

    @Builder
    public UserReview(Long reviewNo, User writtenUser, User recipient, Game game, String reviewContent, Long score) {
        this.reviewNo = reviewNo;
        this.writtenUser = writtenUser;
        this.game = game;
        this.recipient = recipient;
        this.reviewContent = reviewContent;
        this.score = score;
    }

    public void updateReview(RequestUserReviewDTO updatedReviewDTO) {
        if (updatedReviewDTO.getReviewContent() != null) reviewContent = updatedReviewDTO.getReviewContent();
        if (updatedReviewDTO.getScore() != null) score = updatedReviewDTO.getScore();
    }
}
