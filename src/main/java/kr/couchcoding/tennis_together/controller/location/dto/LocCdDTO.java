package kr.couchcoding.tennis_together.controller.location.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import lombok.Data;

@Data
public class LocCdDTO {

    @ApiModelProperty(value = "위치정보 아이디", example = "1", required = true)
    private Long locCdNo;
    @ApiModelProperty(value = "위치정보 시도 아이디", example = "1", required = true)
    private String locSd;
    @ApiModelProperty(value = "위치정보 시군구 아이디", example = "1", required = true)
    private String locSkk;
    @ApiModelProperty(value = "위치정보 시도", example = "서울시", required = true)
    private String locSdName;
    @ApiModelProperty(value = "위치정보 시군구", example = "중구", required = true)
    private String locSkkName;
    @ApiModelProperty(value = "위치정보 상태", example = "1", required = true)
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
