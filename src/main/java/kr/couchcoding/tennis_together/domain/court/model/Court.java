package kr.couchcoding.tennis_together.domain.court.model;

import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "court_info")
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courtNo;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private LocCd locCd;

    @Column(name = "court_name", length = 100)
    private String name;

    @Column(name = "road_adr", length = 100)
    private String roadAdr;

    private String price;

    @Column(name = "org_url")
    private String orgUrl;

    private Double lat;
    private Double lon;

    @Column(name = "court_contact", length = 200)
    private String courtContact;

    @Lob
    @Column(name = "adt_info")
    private String adtInfo;

    @Column(name = "operate_time")
    private String operateTime;

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @Column(name = "act_dv_cd")
    private Character actDvCd = '1';

    @Builder
    public Court(LocCd locCd, String name, String roadAdr, String price, String orgUrl, Double lat, Double lon
            , String operateTime, String courtContact, String adtInfo) {
        this.locCd = locCd;
        this.roadAdr = roadAdr;
        this.name = name;
        this.price = price;
        this.orgUrl = orgUrl;
        this.lat = lat;
        this.operateTime = operateTime;
        this.lon = lon;
        this.courtContact = courtContact;
        this.adtInfo = adtInfo;
    }

}
