package kr.couchcoding.tennis_together.domain.court.model;
import lombok.*;

import javax.persistence.*;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "court_info")
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courtNo;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private LocCd locCd;

    @Column(name = "road_adr", length = 100)
    private String roadAdr;

    private String price;

    @Column(name = "org_url", length = 100)
    private String orgUrl;

    private Double lat;
    private Double lon;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "court_contact", length = 200)
    private String courtContact;

    @Column(name = "adt_info")
    private String adtInfo;

    @Column(name ="reg_dtm")
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    private LocalDateTime updDtm;

    @Builder
    public Court(LocCd locCd, String roadAdr, String price, String orgUrl, Double lat, Double lon
                , LocalDateTime startTime, LocalDateTime endTime, String courtContact, LocalDateTime regDtm
                , LocalDateTime updDtm, String adtInfo) {
        this.locCd = locCd;
        this.roadAdr = roadAdr;
        this.price = price;
        this.orgUrl = orgUrl;
        this.lat = lat;
        this.lon = lon;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courtContact = courtContact;
        this.adtInfo = adtInfo;
        this.regDtm = regDtm;
        this.updDtm = updDtm;


    }

}
