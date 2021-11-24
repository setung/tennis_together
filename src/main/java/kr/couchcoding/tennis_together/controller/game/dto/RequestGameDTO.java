package kr.couchcoding.tennis_together.controller.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestGameDTO {

    @ApiModelProperty(value = "Game 제목", example = "같이 게임 하실분", required = true)
    String title;
    @ApiModelProperty(value = "Game 내용", example = "잠실에서 같이 해요~", required = true)
    String content;
    @ApiModelProperty(value = "Game 모집 성별 타입", example = "무관", required = true)
    String genderType;
    @ApiModelProperty(value = "Game 모집 나이 타입", example = "무관", required = true)
    Integer ageType;
    @ApiModelProperty(value = "Game 모집 경력 타입", example = "무관", required = true)
    Integer historyType;
    @ApiModelProperty(value = "Game 모집 시작일", example = "2021-11-22", required = true)
    LocalDate strDt;
    @ApiModelProperty(value = "Game 모집 종료일", example = "2021-11-22", required = true)
    LocalDate endDt;
    @ApiModelProperty(value = "Court 아이디", example = "1", required = true)
    Long courtNo;
}
