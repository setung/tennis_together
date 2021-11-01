package kr.couchcoding.tennis_together.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class CourtInfo {

    @Id
    @GeneratedValue
    private Long courtNo;
    private String roadAddr;
    private String fee;
    private String orgUrl;
    private Double lat;
    private Double lon;
    private LocalTime startTime;
    private LocalTime endTime;
    private String courtContact;
    private String adtInfo;
    private LocalDateTime fstRegDtm;
    private LocalDateTime lstUpdDtm;

    /*
    보류
    @ManyToOne
    @JoinColumn(name = "loc_sd")
    private LocCd locSd;
    @ManyToOne
    @JoinColumn(name = "loc_skk")
    private LocCd locSkk;*/

}
