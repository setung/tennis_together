package kr.couchcoding.tennis_together.controller.game.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseGameDTO {

    @ApiModelProperty(value = "Game 아이디", example = "1", required = true)
    private Long gameNo;
    @ApiModelProperty(value = "Game 등록한 User 정보", required = true)
    private User gameCreator;
    @ApiModelProperty(value = "Game 제목", example = "같이 게임 하실분", required = true)
    private String title;
    @ApiModelProperty(value = "Game 내용", example = "잠실에서 같이 해요~", required = true)
    private String content;
    @ApiModelProperty(value = "Game 모집 성별 타입", example = "무관", required = true)
    private String genderType;
    @ApiModelProperty(value = "Game 모집 나이 타입", example = "무관", required = true)
    private Integer ageType;
    @ApiModelProperty(value = "Game 모집 경력 타입", example = "무관", required = true)
    private Integer historyType;
    @ApiModelProperty(value = "Game 모집 시작일", example = "2021-11-22", required = true)
    private LocalDate strDt;
    @ApiModelProperty(value = "Game 모집 종료일", example = "2021-11-22", required = true)
    private LocalDate endDt;
    @ApiModelProperty(value = "Game 모집 등록일", example = "2021-11-22", required = true)
    private LocalDateTime regDtm;
    @ApiModelProperty(value = "Game 모집 수정일", example = "2021-11-22 00:47:51", required = true)
    private LocalDateTime updDtm;
    @ApiModelProperty(value = "Court 아이디", example = "2021-11-22 00:47:51", required = true)
    private Court court;
    @ApiModelProperty(value = "Game 상태", example = "DELETED", required = true)
    private GameStatus stDvCd;

    public ResponseGameDTO(Game game) {
        gameNo = game.getGameNo();
        gameCreator = game.getGameCreator();
        title = game.getTitle();
        content = game.getContent();
        genderType = game.getGenderType();
        ageType = game.getAgeType();
        historyType = game.getHistoryType();
        strDt = game.getStrDt();
        endDt = game.getEndDt();
        regDtm = game.getRegDtm();
        updDtm = game.getUpdDtm();
        court = game.getCourt();
        stDvCd = game.getGameStatus();
    }
}
