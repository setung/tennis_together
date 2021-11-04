package kr.couchcoding.tennis_together.domain.court.dto;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CourtDto {

    private long courtNo;
    private LocCd locCd;
    private String name;
    private String roadAdr;
    private String price;
    private String orgUrl;
    private Double lat;
    private Double lon;
    private String courtContact;
    private String adtInfo;
    private String operateTime;
    private LocalDateTime regDtm;
    private LocalDateTime updDtm;
    private Character actDvCd;

    public CourtDto(Court court) {
        courtNo = court.getCourtNo();
        locCd = court.getLocCd();
        name = court.getName();
        roadAdr = court.getRoadAdr();
        price = court.getPrice();
        orgUrl = court.getOrgUrl();
        lat = court.getLat();
        lon = court.getLon();
        courtContact = court.getCourtContact();
        adtInfo = court.getAdtInfo();
        operateTime = court.getOperateTime();
        regDtm = court.getRegDtm();
        updDtm = court.getUpdDtm();
        actDvCd = court.getActDvCd();
    }
}
