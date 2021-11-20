package kr.couchcoding.tennis_together.domain.court.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CourtDto {

    @ApiModelProperty(value = "테니스장 아이디", example = "1", required = true)
    private Long courtNo;
    @ApiModelProperty(value = "위치 정보", example = "서울시 송파구", required = true)
    private LocCd locCd;
    @ApiModelProperty(value = "테니스장 이름", example = "서울 테니스장", required = true)
    private String name;
    @ApiModelProperty(value = "테니스장 주소", example = "서울특별시 송파구 탄천동로 211(잠실동) ", required = true)
    private String roadAdr;
    @ApiModelProperty(value = "가격", example = "유료", required = true)
    private String price;
    @ApiModelProperty(value = "홈페이지", example = "www.tennis.com", required = true)
    private String orgUrl;
    @ApiModelProperty(value = "위도", example = "15.2", required = true)
    private Double lat;
    @ApiModelProperty(value = "경도", example = "20.3", required = true)
    private Double lon;
    @ApiModelProperty(value = "연락처", example = "02-123-1234", required = true)
    private String courtContact;
    @ApiModelProperty(value = "세부사항", required = true)
    private String adtInfo;
    @ApiModelProperty(value = "운영 시간", example = "00:00 ~ 00:00", required = true)
    private String operateTime;
    @ApiModelProperty(value = "등록일", example = "2021-11-11", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "수정일", example = "2021-11-11", required = true)
    private LocalDateTime updDtm;
    @ApiModelProperty(value = "상태 코드", example = "DELETED", required = true)
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
