package kr.couchcoding.tennis_together.domain.friend.model;
import lombok.*;
import javax.persistence.*;

import kr.couchcoding.tennis_together.domain.user.model.User;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "frd_list")
public class FrdList {

    @Id
    @Column(name = "frd_rel_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long frdRelNo;

    @JoinColumn(name = "uid")
    @ManyToOne
    private User user;

    // User 객체와 양방향 매핑 N : 1
    @JoinColumn(name = "frd_uid")
    @ManyToOne
    private User frdUser;

    @Column(name = "rel_st_cd")
    private char relStCd;

    @Column(name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    private LocalDateTime updDtm;

    @Builder
    public FrdList(User frdUser, char relStCd, LocalDateTime regDtm, LocalDateTime updDtm){
        this.frdUser = frdUser;
        //this.friend = friend;
        this.relStCd = relStCd;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
    }
}
