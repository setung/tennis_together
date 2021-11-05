package kr.couchcoding.tennis_together.controller.location.dto;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.Data;

@Data
public class LocCdDTO {

    private Long locCdNo;
    private String locSd;
    private String locSkk;
    private String locSdName;
    private String locSkkName;
    private Character regStCd;

    public LocCdDTO(LocCd locCd) {
        locCdNo = locCd.getLocCdNo();
        locSd = locCd.getLocSd();
        locSkk = locCd.getLocSkk();
        locSdName = locCd.getLocSdName();
        locSkkName = locCd.getLocSkkName();
        regStCd = locCd.getRegStCd();
    }

}
