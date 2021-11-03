package kr.couchcoding.tennis_together.domain.user_review.model;


import lombok.*;
import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.user.model.User;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_review")
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private long reviewNo;

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "written_uid")
    private User reviewUser; // written_uid 칼럼을 객체로

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @Column(name = "score")
    private double score;

    public UserReview(long reviewNo, User reviewUser, String reviewTitle, String reviewContent
                    , double score){
        this.reviewNo = reviewNo;
        this.reviewUser = reviewUser;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.score = score;
    }

}
