package kr.couchcoding.tennis_together.domain.friend.model;
import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

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
    private Long frdRelNo;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    // User 객체와 양방향 매핑 N : 1
    @ManyToOne
    @JoinColumn(name = "frd_uid")
    private User frdUser;

    @Column(name = "rel_st_cd")
    private char relStCd = '1';

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Builder
    public FrdList(User user, User frdUser){
        this.user = user;
        this.frdUser = frdUser;
    }
}
