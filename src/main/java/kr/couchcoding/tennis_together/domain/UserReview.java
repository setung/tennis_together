package kr.couchcoding.tennis_together.domain;


import lombok.*;
import javax.persistence.*;
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
    @JoinColumn(name = "uid")
    private User reviewUser; // written_uid 칼럼을 객체로

    // User 객체와 양방향 매핑 N : 1
    //@ManyToOne
    //@JoinColumn(name = "uid")
    //private User writerUser; // writer_uid 칼럼을 객체로


    @Column(name = "seq_no")
    private long seqNo;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    private LocalDateTime updDtm;

    @Column(name = "score")
    private double score;

    public UserReview(long reviewNo, User reviewUser, User writtenUser, long seqNo, String reviewTitle, String reviewContent
                    , LocalDateTime regDtm, LocalDateTime updDtm, double score){
        this.reviewNo = reviewNo;
        this.reviewUser = reviewUser;
        //this.writtenUser = writtenUser;
        this.seqNo = seqNo;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
        this.score = score;
    }

}
